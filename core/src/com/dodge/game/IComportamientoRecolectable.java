package com.dodge.game;

public interface IComportamientoRecolectable {
    // Método que devuelve los puntos que se otorgan al jugador cuando se recoge el objeto.
    int getPuntos();

    // Método que devuelve la probabilidad de aparición del objeto recolectable en el juego.
    float getProbabilidadAparicion();
}
