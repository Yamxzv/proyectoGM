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
	final GameLluviaMenu game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private Auto auto;
	private Obstaculos obstaculos;
	private Texture fondo;

	public GameScreen(final GameLluviaMenu game) {
		this.game = game;
		this.batch = game.getBatch();
		this.font = game.getFont();

		// Inicializar sonidos y texturas de los objetos
		Sound choqueSound = Gdx.audio.newSound(Gdx.files.internal("choque.mp3"));
		auto = new Auto(new Texture(Gdx.files.internal("autorojo1.png")), choqueSound);

		Texture moneda = new Texture(Gdx.files.internal("moneda.png"));
		Texture roca = new Texture(Gdx.files.internal("roca.png"));
		Texture arbol = new Texture(Gdx.files.internal("arbol.png"));
		Texture hoyo = new Texture(Gdx.files.internal("hoyo.png"));
		Sound coinSound = Gdx.audio.newSound(Gdx.files.internal("moneda.mp3"));
		Music instrumentalMusic = Gdx.audio.newMusic(Gdx.files.internal("instrumental.mp3"));

		obstaculos = new Obstaculos(moneda, roca, arbol, hoyo, coinSound, instrumentalMusic);

		// Cargar la textura del fondo
		fondo = new Texture(Gdx.files.internal("game_background.png"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		auto.crear();
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
		font.draw(batch, "Monedas totales: " + auto.getPuntos(), 5, 475);
		font.draw(batch, "Vidas : " + auto.getVidas(), 670, 475);
		font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);

		// Actualizar movimiento y verificar colisiones
		if (!auto.estaHerido()) {
			auto.actualizarMovimiento();
			if (!obstaculos.actualizarMovimiento(auto)) {
				// Si el juego termina, actualizar HighScore y pasar a la pantalla Game Over
				if (game.getHigherScore() < auto.getPuntos())
					game.setHigherScore(auto.getPuntos());
				game.setScreen(new GameOverScreen(game, auto.getPuntos()));
				dispose();
			}
		}

		auto.dibujar(batch);
		obstaculos.actualizarDibujoLluvia(batch);

		batch.end();
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
		auto.destruir();
		obstaculos.destruir();
		fondo.dispose();
	}
}