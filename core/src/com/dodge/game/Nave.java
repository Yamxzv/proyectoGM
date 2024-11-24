package com.dodge.game;

import com.badlogic.gdx.math.Rectangle;

// Clase que representa una nave como un tipo de Vehículo
public class Nave extends Vehiculo {

    @Override
    protected void definirVelocidad() {
        velocidadBase = 500; // Define la velocidad base de la nave
    }

    @Override
    protected void configurarHitbox() {
        hitbox = new Rectangle(); // Inicializa la hitbox de la nave
        hitbox.width = 42; // Ancho de la hitbox
        hitbox.height = 70; // Altura de la hitbox
    }

    @Override
    protected void configurarPosicionInicial() {
        // Configura la posición inicial centrada horizontalmente y en la parte inferior
        hitbox.x = (float) 800 / 2 - (float) 64 / 2;
        hitbox.y = 20;
    }
}
