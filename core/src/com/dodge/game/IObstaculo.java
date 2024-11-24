package com.dodge.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface IObstaculo {
    // Actualiza el movimiento del obstáculo en cada ciclo del juego.
    void actualizarMovimiento();

    // Dibuja el obstáculo en la pantalla usando un SpriteBatch.
    void dibujar(SpriteBatch batch);

    // Devuelve el área del obstáculo como un objeto Rectangle,
    // lo cual es útil para detectar colisiones.
    Rectangle getArea();

    // Determina si el obstáculo es dañino para el jugador.
    boolean esDañino();

    // Libera cualquier recurso utilizado por el obstáculo (por ejemplo, texturas).
    void destruir();

    // Determina si el obstáculo ha salido de la pantalla.
    boolean estaFueraDePantalla();
}
