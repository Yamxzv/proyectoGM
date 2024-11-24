package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

// Clase que representa obstáculos recolectables en el juego
public class ObstaculoRecolectable implements IObstaculo {
    final private Texture textura; // Textura del recolectable
    final private Rectangle area; // Área de colisión del recolectable
    private static final float ESCALA = 0.15f; // Escala fija para todos los recolectables
    final private IComportamientoRecolectable comportamiento; // Comportamiento asociado al recolectable

    public ObstaculoRecolectable(Texture textura, IComportamientoRecolectable comportamiento) {
        this.textura = textura;
        this.comportamiento = comportamiento;
        this.area = new Rectangle();

        // Inicializa el área de colisión con posición aleatoria y tamaño fijo
        this.area.x = MathUtils.random(0, 800 - 32);
        this.area.y = 480; // Comienza en la parte superior de la pantalla
        this.area.width = 32;
        this.area.height = 32;
    }

    public int getPuntos() {
        return comportamiento.getPuntos(); // Devuelve los puntos otorgados al recolectar
    }

    @Override
    public void actualizarMovimiento() {
        // Mueve el recolectable hacia abajo según el deltaTime
        area.y -= 300 * Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        // Dibuja el recolectable en pantalla con la escala definida
        batch.draw(textura, area.x, area.y,
                textura.getWidth() * ESCALA,
                textura.getHeight() * ESCALA);
    }

    @Override
    public Rectangle getArea() {
        return area; // Devuelve el área de colisión
    }

    @Override
    public boolean esDañino() {
        return false; // Los recolectables no son dañinos
    }

    @Override
    public void destruir() {
        // No libera la textura, ya que es gestionada externamente
    }

    @Override
    public boolean estaFueraDePantalla() {
        // Verifica si el recolectable está fuera de la pantalla
        return area.y + area.height < 0;
    }
}
