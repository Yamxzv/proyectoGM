package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
	private final GameDodgeMenu game;
	final private SpriteBatch batch;
	final private BitmapFont font;
	final private OrthographicCamera camera;

	private final Texture backgroundImage;
	private final Texture gameOverImage;
	private final Texture retryButton;
	private final Texture retryButtonHover;
	private final Texture exitButton;
	private final Texture exitButtonHover;

	private boolean retryHover;
	private boolean exitHover;

	final private int score;

	public GameOverScreen(final GameDodgeMenu game, int score) {
		this.game = game;
		this.batch = game.getBatch();
		this.font = game.getFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480); // Configuración de cámara

		this.backgroundImage = new Texture("game_over_background.png");
		this.gameOverImage = new Texture("game_over_image.png");
		this.retryButton = new Texture("retry_button.png");
		this.retryButtonHover = new Texture("retry_button_hover.png");
		this.exitButton = new Texture("exit_button.png");
		this.exitButtonHover = new Texture("exit_button_hover.png");

		this.retryHover = false; // Estado de hover para botón de retry
		this.exitHover = false;  // Estado de hover para botón de salir

		this.score = score; // Puntaje final del jugador
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);

		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		retryHover = mouseX >= 200 && mouseX <= 348 && mouseY >= 50 && mouseY <= 198;
		exitHover = mouseX >= 450 && mouseX <= 598 && mouseY >= 50 && mouseY <= 198;

		batch.begin();

		batch.draw(backgroundImage, 0, 0, 800, 480);
		batch.draw(gameOverImage, 300, 300, 200, 100);

		font.getData().setScale(1.5f);
		font.draw(batch, "Puntaje: " + score, 350, 240);
		font.draw(batch, "Mejor Puntaje: " + game.getHigherScore(), 350, 210);

		batch.draw(retryHover ? retryButtonHover : retryButton, 200, 50, 148, 148);
		batch.draw(exitHover ? exitButtonHover : exitButton, 450, 50, 148, 148);

		batch.end();

		if (retryHover && Gdx.input.justTouched()) {
			game.setScreen(new GameScreen(game)); // Reinicia el juego
			dispose();
		}
		if (exitHover && Gdx.input.justTouched()) {
			Gdx.app.exit(); // Cierra la aplicación
		}
	}

	@Override
	public void show() {
		// Método invocado al mostrar la pantalla
	}

	@Override
	public void resize(int width, int height) {
		// Ajuste de tamaño de pantalla
	}

	@Override
	public void pause() {
		// Método invocado al pausar la aplicación
	}

	@Override
	public void resume() {
		// Método invocado al reanudar la aplicación
	}

	@Override
	public void hide() {
		// Método invocado al ocultar la pantalla
	}

	@Override
	public void dispose() {
		// Libera los recursos
		backgroundImage.dispose();
		gameOverImage.dispose();
		retryButton.dispose();
		retryButtonHover.dispose();
	}
}
