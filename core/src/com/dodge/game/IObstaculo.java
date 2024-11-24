package com.dodge.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface IObstaculo {
    void actualizarMovimiento();
    void dibujar(SpriteBatch batch);
    Rectangle getArea();
    boolean esDañino();
    void destruir();
    boolean estaFueraDePantalla();
}
