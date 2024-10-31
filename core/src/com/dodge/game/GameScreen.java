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
	private int puntosTotales;
	private int nivelActual;

	public GameScreen(final GameDodgeMenu game) {
		this.game = game;
		this.batch = game.getBatch();
		this.font = game.getFont();
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		this.puntosTotales = 0;
		this.nivelActual = 1;
		inicializarNivel(nivelActual);
	}

	private void inicializarNivel(int nivel) {
		// Liberar recursos anteriores
		if (vehiculo != null) {
			puntosTotales += vehiculo.getPuntos();
			vehiculo.destruir();
		}
		if (obstaculos != null) obstaculos.destruir();
		if (fondo != null) fondo.dispose();

		// Inicializar recursos según el nivel
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
				Texture monedaNivel2 = new Texture(Gdx.files.internal("moneda2.png"));
				Texture nubeNivel2 = new Texture(Gdx.files.internal("nube.png"));
				Texture relampagoNivel2 = new Texture(Gdx.files.internal("relampago.png"));
				Texture pajaroNivel2 = new Texture(Gdx.files.internal("pajaro.png"));
				coinSound = Gdx.audio.newSound(Gdx.files.internal("sonido_moneda2.mp3"));
				instrumentalMusic = Gdx.audio.newMusic(Gdx.files.internal("instrumental2.mp3"));
				obstaculos = new Obstaculos(monedaNivel2, nubeNivel2, relampagoNivel2, pajaroNivel2, coinSound, instrumentalMusic);
				break;

			case 3:
				vehiculo = new Nave(new Texture(Gdx.files.internal("nave.png")), Gdx.audio.newSound(Gdx.files.internal("choque_nave.mp3")));
				fondo = new Texture(Gdx.files.internal("fondo_nivel_3.png"));

				// Texturas y sonidos de obstáculos para el nivel 3
				Texture estrellaNivel3 = new Texture(Gdx.files.internal("moneda3.png"));
				Texture planetaNivel3 = new Texture(Gdx.files.internal("planeta.png"));
				Texture cometaNivel3 = new Texture(Gdx.files.internal("cometa.png"));
				Texture asteroideNivel3 = new Texture(Gdx.files.internal("asteroide.png"));
				coinSound = Gdx.audio.newSound(Gdx.files.internal("sonido_moneda3.mp3"));
				instrumentalMusic = Gdx.audio.newMusic(Gdx.files.internal("instrumental3.mp3"));
				obstaculos = new Obstaculos(estrellaNivel3, planetaNivel3, cometaNivel3, asteroideNivel3, coinSound, instrumentalMusic);
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
		font.draw(batch, "Puntos totales: " + (puntosTotales + vehiculo.getPuntos()), 5, 475);
		font.draw(batch, "Vidas : " + vehiculo.getVidas(), 670, 475);
		font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);

		// Actualizar movimiento y verificar colisiones
		if (!vehiculo.estaHerido()) {
			vehiculo.actualizarMovimiento();
			if (!obstaculos.actualizarMovimiento(vehiculo)) {
				// Si el juego termina, actualizar HighScore y pasar a la pantalla Game Over
				int puntajeFinal = puntosTotales + vehiculo.getPuntos();
				if (game.getHigherScore() < puntajeFinal)
					game.setHigherScore(puntajeFinal);
				game.setScreen(new GameOverScreen(game, puntajeFinal));
				dispose();
			}
		}

		vehiculo.dibujar(batch);
		obstaculos.actualizarDibujoObjeto(batch);
		batch.end();

		verificarCambioNivel();
	}

	private void verificarCambioNivel() {
		if (vehiculo.getPuntos() >= 800 && nivelActual == 1){
			nivelActual = 2;
			inicializarNivel(nivelActual);
		} else if (vehiculo.getPuntos() >= 800 && nivelActual == 2){
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