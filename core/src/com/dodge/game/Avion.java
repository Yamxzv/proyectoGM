package com.dodge.game;

import com.badlogic.gdx.math.Rectangle;

public class Avion extends Vehiculo {
    @Override
    protected void definirVelocidad() {
        velocidadBase = 450; // Define una velocidad base más alta para el avión.
    }

    @Override
    protected void configurarHitbox() {
        hitbox = new Rectangle(); // Inicializa la hitbox específica para el avión.
        hitbox.width = 60; // Ancho de la hitbox del avión.
        hitbox.height = 70; // Altura de la hitbox del avión.
    }

    @Override
    protected void configurarPosicionInicial() {
        // Posiciona el avión centrado horizontalmente y cerca del borde inferior.
        hitbox.x = (float) 800 / 2 - (float) 64 / 2;
        hitbox.y = 20;
    }
}
