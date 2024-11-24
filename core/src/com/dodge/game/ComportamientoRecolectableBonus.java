package com.dodge.game;

public class ComportamientoRecolectableBonus implements IComportamientoRecolectable {

    @Override
    public int getPuntos() {
        return 50; // Define la cantidad de puntos otorgados al recolectar este bonus.
    }

    @Override
    public float getProbabilidadAparicion() {
        return 0.01f; // Probabilidad baja de aparición para este bonus.
    }
}
