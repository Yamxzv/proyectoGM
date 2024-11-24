package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

// Clase que representa obstáculos dañinos en el juego
public class ObstaculoDañino implements IObstaculo {
    final private Texture textura; // Textura del obstáculo
    final private Rectangle area; // Área de colisión del obstáculo
    final private float escala; // Escala para ajustar el tamaño del dibujo
    final private TipoObstaculoDañino tipo; // Tipo específico del obstáculo

    // Tipos de obstáculos dañinos con propiedades específicas
    public enum TipoObstaculoDañino {
        obs1(0.1f, 40, 40), // Escala pequeña y cuadrado
        obs2(0.3f, 30, 60), // Escala media y rectangular
        obs3(0.25f, 60, 10); // Escala media y muy ancho

        private final float escala; // Escala del tipo
        private final float width; // Ancho del área de colisión
        private final float height; // Altura del área de colisión

        TipoObstaculoDañino(float escala, float width, float height) {
            this.escala = escala;
            this.width = width;
            this.height = height;
        }
    }

    public ObstaculoDañino(Texture textura, TipoObstaculoDañino tipo) {
        this.textura = textura;
        this.tipo = tipo;
        this.escala = tipo.escala;
        this.area = new Rectangle();

        // Inicializa el área de colisión con posición aleatoria y tamaño según el tipo
        this.area.x = MathUtils.random(0, 800 - tipo.width);
        this.area.y = 480; // Comienza en la parte superior de la pantalla
        this.area.width = tipo.width;
        this.area.height = tipo.height;
    }

    @Override
    public void actualizarMovimiento() {
        // Mueve el obstáculo hacia abajo según el deltaTime
        area.y -= 300 * Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        // Calcula las coordenadas y dimensiones ajustadas según el tipo
        float drawX = area.x;
        float drawY = area.y;

        if (tipo == TipoObstaculoDañino.obs2) {
            drawX = area.x - ((textura.getWidth() * escala - area.width) / 2);
        } else if (tipo == TipoObstaculoDañino.obs3) {
            drawY = area.y - (textura.getHeight() * escala * 0.2f);
            drawX = area.x - ((textura.getWidth() * escala - area.width) / 2);
        }

        // Dibuja el obstáculo en pantalla
        batch.draw(textura, drawX, drawY,
                textura.getWidth() * escala,
                textura.getHeight() * escala);
    }

    @Override
    public Rectangle getArea() {
        return area; // Devuelve el área de colisión
    }

    @Override
    public boolean esDañino() {
        return true; // Todos los obstáculos de esta clase son dañinos
    }

    @Override
    public void destruir() {
        // No libera la textura, ya que es gestionada externamente
    }

    @Override
    public boolean estaFueraDePantalla() {
        // Verifica si el obstáculo está fuera de la pantalla
        return area.y + area.height < 0;
    }
}
