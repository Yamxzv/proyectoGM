package com.dodge.game;

public class ComportamientoRecolectableNormal implements IComportamientoRecolectable {
    @Override
    public int getPuntos() {
        return 10;
    }

    @Override
    public float getProbabilidadAparicion() {
        return 0.5f;
    }
}