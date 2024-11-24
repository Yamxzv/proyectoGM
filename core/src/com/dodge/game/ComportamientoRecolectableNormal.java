package com.dodge.game;

public class ComportamientoRecolectableNormal implements IComportamientoRecolectable {

    @Override
    public int getPuntos() {
        return 10; // Define los puntos otorgados al recolectar este objeto normal.
    }

    @Override
    public float getProbabilidadAparicion() {
        return 0.5f; // Probabilidad moderada de aparición para este recolectable.
    }
}
