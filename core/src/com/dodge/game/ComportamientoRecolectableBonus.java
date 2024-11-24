package com.dodge.game;

public class ComportamientoRecolectableBonus implements IComportamientoRecolectable {
    @Override
    public int getPuntos() {
        return 50;
    }

    @Override
    public float getProbabilidadAparicion() {
        return 0.01f;
    }
}