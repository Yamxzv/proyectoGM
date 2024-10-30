package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
	private final GameLluviaMenu game;
	private SpriteBatch batch;
	private BitmapFont font;
	private OrthographicCamera camera;

	private final Texture backgroundImage;
	private final Texture gameOverImage;
	private final Texture retryButton;
	private final Texture retryButtonHover;
	private final Texture exitButton;
	private final Texture exitButtonHover;

	private boolean retryHover;
	private boolean exitHover;

	private float opacity = 0;
	private boolean fadingIn = true;

	private int score;

	public GameOverScreen(final GameLluviaMenu game, int score) {
		this.game = game;
		this.batch = game.getBatch();
		this.font = game.getFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		this.backgroundImage = new Texture("game_over_background.png");
		this.gameOverImage = new Texture("game_over_image.png");
		this.retryButton = new Texture("retry_button.png");
		this.retryButtonHover = new Texture("retry_button_hover.png");
		this.exitButton = new Texture("exit_button.png");
		this.exitButtonHover = new Texture("exit_button_hover.png");

		this.retryHover = false;
		this.exitHover = false;

		this.score = score;
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
			game.setScreen(new GameScreen(game));
			dispose();
		}
		if (exitHover && Gdx.input.justTouched()) {
			Gdx.app.exit();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		backgroundImage.dispose();
		gameOverImage.dispose();
		retryButton.dispose();
		retryButtonHover.dispose();
	}
}