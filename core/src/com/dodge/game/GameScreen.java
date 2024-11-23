package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
	final GameDodgeMenu game; // Referencia al juego principal para manejar pantallas y recursos compartidos
	private final OrthographicCamera camera; // Cámara para ajustar la vista del juego
	private final SpriteBatch batch; // Batch para dibujar texturas y sprites
	private final BitmapFont font; // Fuente para mostrar el texto en pantalla
	private Vehiculo vehiculo; // Vehículo del jugador, cambia según el nivel
	private GestorObstaculos gestorObstaculos; // Administrador de obstáculos y monedas en pantalla
	private Texture fondo; // Textura de fondo que cambia en cada nivel
	private int nivelActual; // Nivel actual del juego
	private final ScoreManager scoreManager; // Usando el Singleton ScoreManager

	public GameScreen(final GameDodgeMenu game) {
		this.game = game;
		this.batch = game.getBatch();
		this.font = game.getFont();
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480); // Configuración inicial de la cámara

		this.nivelActual = 1; // Empieza en el nivel 1
		this.scoreManager = ScoreManager.getInstance();
		scoreManager.resetCurrentScore();
		inicializarNivel(nivelActual); // Llama a inicializar el nivel actual
	}

	private void inicializarNivel(int nivel) {
		// Liberar recursos del nivel anterior
		if (vehiculo != null) {
			scoreManager.addPoints(vehiculo.getPuntos()); // Sumar puntos del vehículo anterior
			vehiculo.destruir(); // Liberar recursos del vehículo
		}
		if (gestorObstaculos != null) gestorObstaculos.destruir(); // Liberar recursos del gestor de obstáculos
		if (fondo != null) fondo.dispose(); // Liberar textura del fondo

		// Variables para sonidos de monedas y música de fondo
		Sound coinSound;
		Music instrumentalMusic;

		// Asignación de recursos según el nivel
		switch (nivel) {
			case 1:
				// Configuración del nivel 1: Auto, fondo y obstáculos de carretera
				vehiculo = new Auto();
				vehiculo.inicializar(new Texture(Gdx.files.internal("autorojo1.png")), Gdx.audio.newSound(Gdx.files.internal("choque_auto.mp3")));
				fondo = new Texture(Gdx.files.internal("fondo_nivel_1.png"));
				Texture monedaNivel1 = new Texture(Gdx.files.internal("moneda.png"));
				Texture rocaNivel1 = new Texture(Gdx.files.internal("roca.png"));
				Texture arbolNivel1 = new Texture(Gdx.files.internal("arbol.png"));
				Texture hoyoNivel1 = new Texture(Gdx.files.internal("hoyo.png"));
				coinSound = Gdx.audio.newSound(Gdx.files.internal("moneda.mp3"));
				instrumentalMusic = Gdx.audio.newMusic(Gdx.files.internal("instrumental.mp3"));
				gestorObstaculos = new GestorObstaculos(monedaNivel1, rocaNivel1, arbolNivel1, hoyoNivel1, coinSound, instrumentalMusic);
				break;

			case 2:
				// Configuración del nivel 2: Avión, fondo y obstáculos aéreos
				vehiculo = new Avion();
				vehiculo.inicializar(new Texture(Gdx.files.internal("avion.png")), Gdx.audio.newSound(Gdx.files.internal("choque_avion.mp3")));
				fondo = new Texture(Gdx.files.internal("fondo_nivel_2.png"));
				Texture monedaNivel2 = new Texture(Gdx.files.internal("moneda2.png"));
				Texture nubeNivel2 = new Texture(Gdx.files.internal("nube.png"));
				Texture relampagoNivel2 = new Texture(Gdx.files.internal("relampago.png"));
				Texture pajaroNivel2 = new Texture(Gdx.files.internal("pajaro.png"));
				coinSound = Gdx.audio.newSound(Gdx.files.internal("sonido_moneda2.mp3"));
				instrumentalMusic = Gdx.audio.newMusic(Gdx.files.internal("instrumental2.mp3"));
				gestorObstaculos = new GestorObstaculos(monedaNivel2, nubeNivel2, relampagoNivel2, pajaroNivel2, coinSound, instrumentalMusic);
				break;

			case 3:
				// Configuración del nivel 3: Nave espacial, fondo y obstáculos espaciales
				vehiculo = new Nave();
				vehiculo.inicializar(new Texture(Gdx.files.internal("nave.png")), Gdx.audio.newSound(Gdx.files.internal("choque_nave.mp3")));
				fondo = new Texture(Gdx.files.internal("fondo_nivel_3.png"));
				Texture estrellaNivel3 = new Texture(Gdx.files.internal("moneda3.png"));
				Texture planetaNivel3 = new Texture(Gdx.files.internal("planeta.png"));
				Texture cometaNivel3 = new Texture(Gdx.files.internal("cometa.png"));
				Texture asteroideNivel3 = new Texture(Gdx.files.internal("asteroide.png"));
				coinSound = Gdx.audio.newSound(Gdx.files.internal("sonido_moneda3.mp3"));
				instrumentalMusic = Gdx.audio.newMusic(Gdx.files.internal("instrumental3.mp3"));
				gestorObstaculos = new GestorObstaculos(estrellaNivel3, planetaNivel3, cometaNivel3, asteroideNivel3, coinSound, instrumentalMusic);
				break;
		}

		// Creación de los recursos del vehículo y los obstáculos
		vehiculo.crear();
		gestorObstaculos.crear();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1); // Limpia la pantalla antes de dibujar
		camera.update();
		batch.setProjectionMatrix(camera.combined); // Actualiza la cámara

		batch.begin();

		// Dibujar fondo y HUD
		batch.draw(fondo, 0, 0, 800, 480);
		font.draw(batch, "Puntos totales: " + scoreManager.getCurrentScore(), 5, 475);
		font.draw(batch, "Vidas : " + vehiculo.getVidas(), 670, 475);
		font.draw(batch, "HighScore : " + scoreManager.getHighScore(), camera.viewportWidth / 2 - 50, 475);

		// Actualizar movimiento y verificar colisiones
		if (!vehiculo.estaHerido()) {
			vehiculo.actualizarMovimiento();
			if (!gestorObstaculos.actualizarMovimiento(vehiculo)) {
				// Si el juego termina, actualizar HighScore y pasar a la pantalla Game Over
				int puntajeFinal = scoreManager.getCurrentScore() + vehiculo.getPuntos();
				if (scoreManager.getHighScore() < puntajeFinal)
					scoreManager.resetHighScore();
				game.setScreen(new GameOverScreen(game, puntajeFinal));
				dispose(); // Liberar recursos
			}
		}

		// Dibujar el vehículo y obstáculos
		vehiculo.dibujar(batch);
		gestorObstaculos.actualizarDibujoObjeto(batch);
		batch.end();

		verificarCambioNivel(); // Verifica si es necesario cambiar de nivel
	}

	private void verificarCambioNivel() {
		// Cambio de nivel si el puntaje supera cierto umbral
		int puntajeActual = scoreManager.getCurrentScore();

		if (puntajeActual >= 800 && nivelActual == 1) {
			nivelActual = 2;
			inicializarNivel(nivelActual);
		} else if (puntajeActual >= 1600 && nivelActual == 2) {
			nivelActual = 3;
			inicializarNivel(nivelActual);
		}
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void show() {
		gestorObstaculos.continuar(); // Reanuda obstáculos si estaban pausados
	}

	@Override
	public void hide() {}

	@Override
	public void pause() {
		gestorObstaculos.pausar(); // Pausa los obstáculos
		game.setScreen(new PausaScreen(game, this)); // Muestra pantalla de pausa
	}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		// Liberar todos los recursos al salir de la pantalla
		vehiculo.destruir();
		gestorObstaculos.destruir();
		fondo.dispose();
	}
}
