package com.dodge.game;

import com.badlogic.gdx.math.Rectangle;

public class Auto extends Vehiculo {

	@Override
	protected void definirVelocidad() {
		velocidadBase = 400; // Establece la velocidad base del vehículo.
	}

	@Override
	protected void configurarHitbox() {
		hitbox = new Rectangle(); // Inicializa la hitbox del vehículo.
		hitbox.width = 42; // Ancho de la hitbox.
		hitbox.height = 70; // Altura de la hitbox.
	}

	@Override
	protected void configurarPosicionInicial() {
		// Calcula la posición inicial centrando el vehículo horizontalmente y ubicándolo cerca del borde inferior.
		hitbox.x = (float) 800 / 2 - (float) 64 / 2;
		hitbox.y = 20;
	}
}
