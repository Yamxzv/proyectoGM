package com.dodge.game;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
			scoreManager.addPoints(vehiculo.getPuntos());
			vehiculo.destruir();
		}
		if (gestorObstaculos != null) gestorObstaculos.destruir();
		if (fondo != null) fondo.dispose();

		// Usar el patrón Factory para crear el nivel
		FabricaNiveles factory;
		switch (nivel) {
			case 1:
				factory = new CrearTierra();
				break;
			case 2:
				factory = new CrearAire();
				break;
			case 3:
				factory = new CrearEspacio();
				break;
			default:
				throw new IllegalArgumentException("Nivel no válido: " + nivel);
		}

		// Crear todos los elementos del nivel usando la fábrica
		vehiculo = factory.crearVehiculo();
		fondo = factory.crearFondo();
		gestorObstaculos = factory.crearGestorObstaculos();

		// Inicializar los elementos
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
		font.setColor(Color.RED);
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
