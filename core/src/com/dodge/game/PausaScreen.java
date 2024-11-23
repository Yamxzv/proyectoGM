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

	private final Texture backgroundImage; // Textura del fondo
	private final Texture resumeButton; // Botón de reanudar
	private final Texture resumeButtonHover; // Botón de reanudar (hover)
	private final Texture exitButton; // Botón de salir
	private final Texture exitButtonHover; // Botón de salir (hover)

	private boolean resumeHover; // Estado del botón de reanudar
	private boolean exitHover; // Estado del botón de salir

	public PausaScreen(final GameDodgeMenu game, GameScreen juego) {
		this.game = game;
		this.juego = juego;
		this.batch = game.getBatch();
		this.font = game.getFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480); // Configura la cámara

		// Carga las texturas
		this.backgroundImage = new Texture("background.png");
		this.resumeButton = new Texture("play_button.png");
		this.resumeButtonHover = new Texture("play_button_hover.png");
		this.exitButton = new Texture("exit_button.png");
		this.exitButtonHover = new Texture("exit_button_hover.png");

		this.resumeHover = false; // Inicializa el estado del botón de reanudar
		this.exitHover = false; // Inicializa el estado del botón de salir
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.411f, 0.411f, 0.411f, 1); // Limpia la pantalla

		camera.update(); // Actualiza la cámara
		batch.setProjectionMatrix(camera.combined); // Establece la matriz de proyección

		// Obtiene la posición del mouse
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		// Verifica si el mouse está sobre los botones
		resumeHover = mouseX >= 100 && mouseX <= 248 && mouseY >= 150 && mouseY <= 298;
		exitHover = mouseX >= 552 && mouseX <= 700 && mouseY >= 150 && mouseY <= 298;

		batch.begin();
		batch.draw(backgroundImage, 0, 0, 800, 480); // Dibuja el fondo
		font.draw(batch, "Juego en Pausa ", 320, 400); // Dibuja el texto

		// Dibuja los botones con efecto hover
		batch.draw(resumeHover ? resumeButtonHover : resumeButton, 100, 150, 148, 148);
		batch.draw(exitHover ? exitButtonHover : exitButton, 552, 150, 148, 148);
		batch.end();

		// Maneja las interacciones del mouse
		if (resumeHover && Gdx.input.justTouched()) {
			game.setScreen(juego); // Reanuda el juego
			dispose();
		}
		if (exitHover && Gdx.input.justTouched()) {
			Gdx.app.exit(); // Sale de la aplicación
		}
	}

	@Override
	public void show() {
		// Método no implementado
	}

	@Override
	public void resize(int width, int height) {
		// Método no implementado
	}

	@Override
	public void pause() {
		// Método no implementado
	}

	@Override
	public void resume() {
		// Método no implementado
	}

	@Override
	public void hide() {
		// Método no implementado
	}

	@Override
	public void dispose() {
		// Libera las texturas
		backgroundImage.dispose();
		resumeButton.dispose();
		resumeButtonHover.dispose();
		exitButton.dispose();
		exitButtonHover.dispose();
	}
}
