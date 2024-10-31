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
	final GameDodgeMenu game;
	final private OrthographicCamera camera;
	final private SpriteBatch batch;
	final private BitmapFont font;
	private Vehiculo vehiculo;
	private Obstaculos obstaculos;
	private Texture fondo;
	private int puntaje;
	private int nivelActual;

	public GameScreen(final GameDodgeMenu game) {
		this.game = game;
		this.batch = game.getBatch();
		this.font = game.getFont();
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		this.puntaje = 0;
		this.nivelActual = 1;
		inicializarNivel(nivelActual);
	}

	private void inicializarNivel(int nivel) {
		// Liberar recursos anteriores
		if (vehiculo != null) vehiculo.destruir();
		if (obstaculos != null) obstaculos.destruir();
		if (fondo != null) fondo.dispose();

		// Inicializar recursos según el nivel
		Sound choqueSound;
		Sound coinSound;
		Music instrumentalMusic;

		switch (nivel) {
			case 1:
				vehiculo = new Auto(new Texture(Gdx.files.internal("autorojo1.png")), Gdx.audio.newSound(Gdx.files.internal("choque_auto.mp3")));
				fondo = new Texture(Gdx.files.internal("fondo_nivel_1.png"));

				// Texturas y sonidos de obstáculos para el nivel 1
				Texture monedaNivel1 = new Texture(Gdx.files.internal("moneda.png"));
				Texture rocaNivel1 = new Texture(Gdx.files.internal("roca.png"));
				Texture arbolNivel1 = new Texture(Gdx.files.internal("arbol.png"));
				Texture hoyoNivel1 = new Texture(Gdx.files.internal("hoyo.png"));
				coinSound = Gdx.audio.newSound(Gdx.files.internal("moneda.mp3"));
				instrumentalMusic = Gdx.audio.newMusic(Gdx.files.internal("instrumental.mp3"));
				obstaculos = new Obstaculos(monedaNivel1, rocaNivel1, arbolNivel1, hoyoNivel1, coinSound, instrumentalMusic);
				break;

			case 2:
				vehiculo = new Avion(new Texture(Gdx.files.internal("avion.png")), Gdx.audio.newSound(Gdx.files.internal("choque_avion.mp3")));
				fondo = new Texture(Gdx.files.internal("fondo_nivel_2.png"));

				// Texturas y sonidos de obstáculos para el nivel 2
				Texture monedaNivel2 = new Texture(Gdx.files.internal("moneda_nivel2.png"));
				Texture nubeNivel2 = new Texture(Gdx.files.internal("nube.png"));
				Texture pajaroNivel2 = new Texture(Gdx.files.internal("pajaro.png"));
				Texture relampagoNivel2 = new Texture(Gdx.files.internal("relampago.png"));
				coinSound = Gdx.audio.newSound(Gdx.files.internal("sonido_moneda2.mp3"));
				instrumentalMusic = Gdx.audio.newMusic(Gdx.files.internal("musica_nivel2.mp3"));
				obstaculos = new Obstaculos(monedaNivel2, nubeNivel2, pajaroNivel2, relampagoNivel2, coinSound, instrumentalMusic);
				break;

			case 3:
				vehiculo = new Nave(new Texture(Gdx.files.internal("nave.png")), Gdx.audio.newSound(Gdx.files.internal("choque_nave.mp3")));
				fondo = new Texture(Gdx.files.internal("fondo_nivel_3.png"));

				// Texturas y sonidos de obstáculos para el nivel 3
				Texture estrellaNivel3 = new Texture(Gdx.files.internal("estrella.png"));
				Texture asteroideNivel3 = new Texture(Gdx.files.internal("asteroide.png"));
				Texture planetaNivel3 = new Texture(Gdx.files.internal("planeta.png"));
				Texture cometaNivel3 = new Texture(Gdx.files.internal("cometa.png"));
				coinSound = Gdx.audio.newSound(Gdx.files.internal("sonido_moneda3.mp3"));
				instrumentalMusic = Gdx.audio.newMusic(Gdx.files.internal("musica_nivel3.mp3"));
				obstaculos = new Obstaculos(estrellaNivel3, asteroideNivel3, planetaNivel3, cometaNivel3, coinSound, instrumentalMusic);
				break;
		}

        vehiculo.crear();
		obstaculos.crear();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		// Dibujar fondo y HUD
		batch.draw(fondo, 0, 0, 800, 480);
		font.draw(batch, "Puntos totales: " + vehiculo.getPuntos(), 5, 475);
		font.draw(batch, "Vidas : " + vehiculo.getVidas(), 670, 475);
		font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);

		// Actualizar movimiento y verificar colisiones
		if (!vehiculo.estaHerido()) {
			vehiculo.actualizarMovimiento();
			if (!obstaculos.actualizarMovimiento(vehiculo)) {
				// Si el juego termina, actualizar HighScore y pasar a la pantalla Game Over
				if (game.getHigherScore() < vehiculo.getPuntos())
					game.setHigherScore(vehiculo.getPuntos());
				game.setScreen(new GameOverScreen(game, vehiculo.getPuntos()));
				dispose();
			}
		}

		vehiculo.dibujar(batch);
		obstaculos.actualizarDibujoObjeto(batch);
		batch.end();

		actualizarPuntaje(delta);
		verificarCambioNivel();
	}

	private void actualizarPuntaje(float delta) {
		puntaje = vehiculo.getPuntos();
	}

	private void verificarCambioNivel() {
		if (puntaje >= 1000 && nivelActual == 1){
			nivelActual = 2;
			inicializarNivel(nivelActual);
		} else if (puntaje >= 2000 && nivelActual == 2){
			nivelActual = 3;
			inicializarNivel(nivelActual);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		obstaculos.continuar();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
		obstaculos.pausar();
		game.setScreen(new PausaScreen(game, this));
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		vehiculo.destruir();
		obstaculos.destruir();
		fondo.dispose();
	}
}