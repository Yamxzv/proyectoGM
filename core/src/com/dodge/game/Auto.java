package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Auto extends Vehiculo {

	public Auto(Texture imagen, Sound sonidoChoque) {
		super(imagen, sonidoChoque, 400);
		crear();
	}

	@Override
	public void crear(){
		hitbox.width = 42;
		hitbox.height = 70;
		hitbox.x = (float) 800 / 2 - (float) 64 / 2;
		hitbox.y = 20;
	}

	@Override
	public void actualizarMovimiento() {
		// Movimiento desde mouse/touch
		/*if(Gdx.input.isTouched()) {
			    Vector3 touchPos = new Vector3();
			    touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			    camera.unproject(touchPos);
			    bucket.x = touchPos.x - 64 / 2;
		}*/
		// Movimiento desde teclado
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) hitbox.x -= velocidad * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) hitbox.x += velocidad * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) hitbox.y += velocidad * Gdx.graphics.getDeltaTime(); // Movimiento hacia arriba
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) hitbox.y -= velocidad * Gdx.graphics.getDeltaTime(); // Movimineto hacia abajo
		// Que no se salga de los bordes izquierda, derecha, arriba y abajo.
		if(hitbox.x < 0) hitbox.x = 0;
		if(hitbox.x > 800 - 64) hitbox.x = 800 - 64;
		if(hitbox.y < 0) hitbox.y = 0; // Limite inferior
		if(hitbox.y > 480 - hitbox.height) hitbox.y = 480 - hitbox.height; // Limite Superior
	}

	@Override
	public void actualizarEstado(){
		if (herido){
			tiempoHerido--;
			if (tiempoHerido <= 0) herido = false;
		}
	}

	@Override
	public void dibujar(SpriteBatch batch) {
		if (!herido)
			batch.draw(imagen, hitbox.x, hitbox.y);
		else {
			batch.draw(imagen, hitbox.x, hitbox.y+ MathUtils.random(-8,8));
			tiempoHerido--;
			if (tiempoHerido <= 0) herido = false;
		}
	}

}
