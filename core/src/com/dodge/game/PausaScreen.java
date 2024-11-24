package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PausaScreen implements Screen {

	private final GameDodgeMenu game; // Referencia al juego principal
	final private GameScreen juego; // Referencia a la pantalla del juego
	final private SpriteBatch batch; // Batch para dibujar
	final private BitmapFont font; // Fuente para texto
	final private OrthographicCamera camera; // Cámara para la proyección

	private final Texture backgroundImage; // Fondo
	private final Texture resumeButton; // Botón de reanudar
	private final Texture resumeButtonHover; // Botón hover de reanudar
	private final Texture exitButton; // Botón de salir
	private final Texture exitButtonHover; // Botón hover de salir

	private boolean resumeHover; // Estado hover de reanudar
	private boolean exitHover; // Estado hover de salir

	public PausaScreen(final GameDodgeMenu game, GameScreen juego) {
		this.game = game;
		this.juego = juego;
		this.batch = game.getBatch();
		this.font = game.getFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480); // Cámara ortográfica

		// Texturas
		this.backgroundImage = new Texture("background.png");
		this.resumeButton = new Texture("play_button.png");
		this.resumeButtonHover = new Texture("play_button_hover.png");
		this.exitButton = new Texture("exit_button.png");
		this.exitButtonHover = new Texture("exit_button_hover.png");

		this.resumeHover = false; // Inicializa estado hover de reanudar
		this.exitHover = false; // Inicializa estado hover de salir
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.411f, 0.411f, 0.411f, 1); // Limpia fondo gris

		camera.update(); // Actualiza cámara
		batch.setProjectionMatrix(camera.combined); // Matriz proyección

		// Coordenadas mouse
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		// Hover en botones
		resumeHover = mouseX >= 100 && mouseX <= 248 && mouseY >= 150 && mouseY <= 298;
		exitHover = mouseX >= 552 && mouseX <= 700 && mouseY >= 150 && mouseY <= 298;

		batch.begin();
		batch.draw(backgroundImage, 0, 0, 800, 480); // Dibuja fondo
		font.draw(batch, "Juego en Pausa ", 320, 400); // Texto título

		// Botones con hover
		batch.draw(resumeHover ? resumeButtonHover : resumeButton, 100, 150, 148, 148);
		batch.draw(exitHover ? exitButtonHover : exitButton, 552, 150, 148, 148);
		batch.end();

		// Acciones de clic
		if (resumeHover && Gdx.input.justTouched()) {
			game.setScreen(juego); // Reanudar juego
			dispose();
		}
		if (exitHover && Gdx.input.justTouched()) {
			Gdx.app.exit(); // Salir del juego
		}
	}

	@Override
	public void show() {
		// Sin implementación
	}

	@Override
	public void resize(int width, int height) {
		// Sin implementación
	}

	@Override
	public void pause() {
		// Sin implementación
	}

	@Override
	public void resume() {
		// Sin implementación
	}

	@Override
	public void hide() {
		// Sin implementación
	}

	@Override
	public void dispose() {
		// Libera recursos
		backgroundImage.dispose();
		resumeButton.dispose();
		resumeButtonHover.dispose();
		exitButton.dispose();
		exitButtonHover.dispose();
	}
}
