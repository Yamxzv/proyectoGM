package com.dodge.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface IObstaculo {
    void actualizarMovimiento(); // Actualiza el movimiento del obstáculo
    void dibujar(SpriteBatch batch); // Dibuja el obstáculo en pantalla
    Rectangle getArea(); // Retorna el área de colisión
    boolean esDañino(); // Verifica si el obstáculo es dañino
    void destruir(); // Libera los recursos del obstáculo
}
