package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

	// Referencia al juego principal y objetos necesarios para la pantalla
	final GameDodgeMenu game;
	final private SpriteBatch batch;
	final private OrthographicCamera camera;

	// Texturas para el fondo, logo y botones
	private final Texture backgroundImage;
	private final Texture logo;
	private final Texture playButton;
	private final Texture playButtonHover;
	private final Texture exitButton;
	private final Texture exitButtonHover;

	// Variables para saber si el mouse está sobre los botones
	private boolean playHover;
	private boolean exitHover;

	public MainMenuScreen(final GameDodgeMenu game) {
		this.game = game;
		this.batch = game.getBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480); // Configura la cámara para una vista 2D

		// Carga las texturas necesarias
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
		ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1); // Limpia la pantalla con un color gris
		batch.setProjectionMatrix(camera.combined);

		// Obtiene las coordenadas del mouse
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		// Detecta si el mouse está sobre los botones
		playHover = mouseX >= 100 && mouseX <= 248 && mouseY >= 150 && mouseY <= 298;
		exitHover = mouseX >= 552 && mouseX <= 700 && mouseY >= 150 && mouseY <= 298;

		batch.begin();
		// Dibuja el fondo, logo y botones
		batch.draw(backgroundImage, 0, 0, 800, 480);
		batch.draw(logo, 300, 250, 200, 200);
		batch.draw(playHover ? playButtonHover : playButton, 100, 150, 148, 148);
		batch.draw(exitHover ? exitButtonHover : exitButton, 552, 150, 148, 148);
		batch.end();

		// Cambia de pantalla o cierra la aplicación según el botón clicado
		if (playHover && Gdx.input.justTouched()){
			game.setScreen(new GameScreen(game)); // Cambia a la pantalla de juego
			dispose(); // Libera recursos de la pantalla actual
		}
		if (exitHover && Gdx.input.justTouched()){
			Gdx.app.exit(); // Cierra la aplicación
		}
	}

	@Override
	public void show() {
		// Llamado al mostrar la pantalla (no usado aquí)
	}

	@Override
	public void resize(int width, int height) {
		// Llamado al cambiar el tamaño de la ventana (no usado aquí)
	}

	@Override
	public void pause() {
		// Llamado al pausar la aplicación (no usado aquí)
	}

	@Override
	public void resume() {
		// Llamado al reanudar la aplicación (no usado aquí)
	}

	@Override
	public void hide() {
		// Llamado al ocultar la pantalla (no usado aquí)
	}

	@Override
	public void dispose() {
		// Libera los recursos cargados en la pantalla
		backgroundImage.dispose();
		logo.dispose();
		playButton.dispose();
		playButtonHover.dispose();
		exitButton.dispose();
		exitButtonHover.dispose();
	}
}
