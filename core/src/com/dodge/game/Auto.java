package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class Auto extends Vehiculo {
	@Override
	protected void definirVelocidad() {
		velocidadBase = 400;
	}

	@Override
	protected void configurarHitbox(){
		hitbox = new Rectangle(); // Crea el rectángulo de colisión
		hitbox.width = 42;
		hitbox.height = 70;
	}

	protected void configurarPosicionInicial(){
		hitbox.x = (float) 800 / 2 - (float) 64 / 2;
		hitbox.y = 20;
	}

	@Override
	public void actualizarMovimiento(){
		actualizarEstadoHerido();
		// Movimiento desde teclado
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) hitbox.x -= velocidadBase * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) hitbox.x += velocidadBase * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) hitbox.y += velocidadBase * Gdx.graphics.getDeltaTime(); // Arriba
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) hitbox.y -= velocidadBase * Gdx.graphics.getDeltaTime(); // Abajo
		// Restricción de bordes
		if(hitbox.x < 0) hitbox.x = 0;
		if(hitbox.x > 800 - 64) hitbox.x = 800 - 64;
		if(hitbox.y < 0) hitbox.y = 0;
		if(hitbox.y > 480 - hitbox.height) hitbox.y = 480 - hitbox.height;

	}
}
