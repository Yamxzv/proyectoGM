package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

	private final Texture backgroundImage; // Imagen de fondo
	private final Texture gameOverImage;  // Imagen de Game Over
	private final Texture retryButton;    // Imagen del botón de retry
	private final Texture retryButtonHover; // Imagen del botón de retry en hover
	private final Texture exitButton;     // Imagen del botón de salir
	private final Texture exitButtonHover; // Imagen del botón de salir en hover

	private boolean retryHover; // Estado de hover del botón de retry
	private boolean exitHover;  // Estado de hover del botón de salir

	final private int score; // Puntaje final del jugador

	public GameOverScreen(final GameDodgeMenu game, int score) {
		this.game = game;
		this.batch = game.getBatch();
		this.font = game.getFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480); // Configuración de cámara

		// Carga de texturas para el fondo, imágenes y botones
		this.backgroundImage = new Texture("game_over_background.png");
		this.gameOverImage = new Texture("game_over_image.png");
		this.retryButton = new Texture("retry_button.png");
		this.retryButtonHover = new Texture("retry_button_hover.png");
		this.exitButton = new Texture("exit_button.png");
		this.exitButtonHover = new Texture("exit_button_hover.png");

		// Inicializa los estados de los botones
		this.retryHover = false;
		this.exitHover = false;

		this.score = score; // Guarda el puntaje al momento del Game Over
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1); // Limpia la pantalla con fondo negro

		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		// Verifica si el mouse está sobre el botón de retry o exit
		retryHover = mouseX >= 200 && mouseX <= 348 && mouseY >= 50 && mouseY <= 198;
		exitHover = mouseX >= 450 && mouseX <= 598 && mouseY >= 50 && mouseY <= 198;

		batch.begin();

		// Dibuja el fondo, la imagen de Game Over y el puntaje en pantalla
		batch.draw(backgroundImage, 0, 0, 800, 480);
		batch.draw(gameOverImage, 300, 300, 200, 100);

		// Dibuja el puntaje y mejor puntaje en pantalla con la fuente
		font.getData().setScale(1.5f);
		font.setColor(Color.BLACK);
		font.draw(batch, "Puntaje: " + score, 350, 240);
		font.draw(batch, "Mejor Puntaje: " + game.getHigherScore(), 350, 210);

		// Dibuja los botones, cambiando la imagen si está en hover
		batch.draw(retryHover ? retryButtonHover : retryButton, 200, 50, 148, 148);
		batch.draw(exitHover ? exitButtonHover : exitButton, 450, 50, 148, 148);

		batch.end();

		// Verifica si se hizo click en algún botón
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
		// Libera los recursos (texturas y sonidos) al finalizar
		backgroundImage.dispose();
		gameOverImage.dispose();
		retryButton.dispose();
		retryButtonHover.dispose();
	}
}
