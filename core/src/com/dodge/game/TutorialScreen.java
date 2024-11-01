package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TutorialScreen implements Screen {
    final GameDodgeMenu game; // Referencia al juego principal
    final private SpriteBatch batch; // Batch para dibujar
    final private BitmapFont font; // Fuente para texto
    final private OrthographicCamera camera; // Cámara para la proyección

    private final Texture backgroundImage; // Textura del fondo
    private final Texture arrowKeysIcon; // Icono de las flechas
    private final Texture progressBarBg; // Fondo de la barra de progreso
    private final Texture progressBarFill; // Llenado de la barra de progreso

    private float timeElapsed; // Tiempo transcurrido
    private float opacity = 0; // Opacidad para el texto
    private boolean fadingIn = true; // Estado de desvanecimiento

    public TutorialScreen(final GameDodgeMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480); // Configura la cámara

        // Carga las texturas
        this.backgroundImage = new Texture("background.png");
        this.arrowKeysIcon = new Texture("arrow_keys.png");
        this.progressBarBg = new Texture("progress_bar_bg.png");
        this.progressBarFill = new Texture("progress_bar_fill.png");

        this.timeElapsed = 0; // Inicializa el tiempo transcurrido
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1); // Limpia la pantalla

        timeElapsed += delta; // Aumenta el tiempo transcurrido

        // Manejo del desvanecimiento
        if (fadingIn) {
            opacity += delta; // Aumenta la opacidad
            if (opacity >= 1) fadingIn = false; // Cambia el estado si alcanza 1
        } else {
            opacity -= delta * 0.5f; // Disminuye la opacidad
            if (opacity <= 0.5f) fadingIn = true; // Cambia el estado si alcanza 0.5
        }

        float progress = Math.min(timeElapsed / 5, 1); // Calcula el progreso

        batch.setProjectionMatrix(camera.combined); // Establece la matriz de proyección

        batch.begin();
        batch.draw(backgroundImage, 0, 0, 800, 480); // Dibuja el fondo

        font.getData().setScale(2); // Escala de fuente
        font.setColor(1, 1, 0, 1); // Color amarillo
        font.draw(batch, "Instrucciones del juego:", 100, 440); // Título

        // Explicación del objetivo
        font.getData().setScale(1.2f); // Cambia la escala de fuente
        font.setColor(1, 1, 1, 1); // Color blanco
        font.draw(batch, "Objetivo: Conduce el vehículo y esquiva todos los obstáculos", 120, 400);
        font.draw(batch, "para lograr el máximo de puntos posibles. ¡Evita chocar!.", 120, 370);

        // Controles
        batch.draw(arrowKeysIcon, 50, 300, 64, 64); // Dibuja el icono de flechas
        font.draw(batch, "Usa las flechas para mover el auto a todas las direcciones!", 120, 330);

        font.setColor(1, 1, 1, opacity); // Cambia la opacidad del texto
        font.draw(batch, "Cargando...", 100, 250); // Texto de carga

        batch.draw(progressBarBg, 100, 200, 600, 20); // Dibuja la barra de progreso
        batch.draw(progressBarFill, 100, 200, 600 * progress, 20); // Dibuja el llenado de la barra

        batch.end();

        // Cambia a la pantalla del menú principal al terminar la carga o al presionar ENTER
        if (progress >= 1 || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MainMenuScreen(game));
            dispose(); // Libera recursos
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
        arrowKeysIcon.dispose();
        progressBarBg.dispose();
        progressBarFill.dispose();
    }
}
