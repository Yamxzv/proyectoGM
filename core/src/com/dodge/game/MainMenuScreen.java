package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

	final GameDodgeMenu game;
	final private SpriteBatch batch;
	final private BitmapFont font;
	final private OrthographicCamera camera;

	private final Texture backgroundImage;
	private final Texture logo;
	private final Texture playButton;
	private final Texture playButtonHover;
	private final Texture exitButton;
	private final Texture exitButtonHover;

	private boolean playHover;
	private boolean exitHover;

	public MainMenuScreen(final GameDodgeMenu game) {
		this.game = game;
		this.batch = game.getBatch();
		this.font = game.getFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// Carga de imágenes
		this.backgroundImage = new Texture("menu_background.png");
		this.logo = new Texture("logo.png");
		this.playButton = new Texture("play_button.png");
		this.playButtonHover = new Texture("play_button_hover.png");
		this.exitButton = new Texture("exit_button.png");
		this.exitButtonHover = new Texture("exit_button_hover.png");

		this.playHover = false;
		this.exitHover = false;
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);
		batch.setProjectionMatrix(camera.combined);

		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		// Verificación de hover sobre botones
		playHover = mouseX >= 100 && mouseX <= 248 && mouseY >= 150 && mouseY <= 298;
		exitHover = mouseX >= 552 && mouseX <= 700 && mouseY >= 150 && mouseY <= 298;

		batch.begin();
		// Dibuja fondo y logo
		batch.draw(backgroundImage, 0, 0, 800, 480);
		batch.draw(logo, 300, 250, 200, 200);

		// Dibuja botones con efecto hover
		batch.draw(playHover ? playButtonHover : playButton, 100, 150, 148, 148);
		batch.draw(exitHover ? exitButtonHover : exitButton, 552, 150, 148, 148);

		batch.end();

		// Acciones al hacer clic
		if (playHover && Gdx.input.justTouched()){
			game.setScreen(new GameScreen(game));
			dispose();
		}
		if (exitHover && Gdx.input.justTouched()){
			Gdx.app.exit();
		}
	}

	@Override
	public void show() {
		// Método vacío
	}

	@Override
	public void resize(int width, int height) {
		// Método vacío
	}

	@Override
	public void pause() {
		// Método vacío
	}

	@Override
	public void resume() {
		// Método vacío
	}

	@Override
	public void hide() {
		// Método vacío
	}

	@Override
	public void dispose() {
		// Libera recursos
		backgroundImage.dispose();
		logo.dispose();
		playButton.dispose();
		playButtonHover.dispose();
		exitButton.dispose();
		exitButtonHover.dispose();
	}
}
