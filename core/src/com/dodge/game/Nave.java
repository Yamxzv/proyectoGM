package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Nave extends Vehiculo {

    public Nave(Texture imagen, Sound sonidoChoque) {
        super(imagen, sonidoChoque, 300);
        crear(); // Llama al método para crear la hitbox
    }

    @Override
    public void crear(){
        hitbox.width = 42;
        hitbox.height = 70;
        hitbox.x = (float) 800 / 2 - (float) 64 / 2; // Centra horizontalmente
        hitbox.y = 20; // Posición inicial en Y
    }

    @Override
    public void actualizarMovimiento() {
        // Movimiento basado en teclas
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) hitbox.x -= velocidad * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) hitbox.x += velocidad * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) hitbox.y += velocidad * Gdx.graphics.getDeltaTime(); // Movimiento hacia arriba
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) hitbox.y -= velocidad * Gdx.graphics.getDeltaTime(); // Movimiento hacia abajo

        // Limita el movimiento para que no se salga de los bordes
        if(hitbox.x < 0) hitbox.x = 0;
        if(hitbox.x > 800 - 64) hitbox.x = 800 - 64;
        if(hitbox.y < 0) hitbox.y = 0; // Límite inferior
        if(hitbox.y > 480 - hitbox.height) hitbox.y = 480 - hitbox.height; // Límite superior
    }

    @Override
    public void actualizarEstado(){
        // Manejo del estado herido
        if (herido){
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false; // Restablece el estado después de un tiempo
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        float escalaX = hitbox.width / imagen.getWidth();
        float escalaY = hitbox.height / imagen.getHeight();

        if (!herido) {
            batch.draw(imagen, hitbox.x, hitbox.y, imagen.getWidth() * escalaX, imagen.getHeight() * escalaY);
        } else {
            // Efecto de temblor cuando está herido
            batch.draw(imagen, hitbox.x, hitbox.y + MathUtils.random(-8, 8), imagen.getWidth() * escalaX, imagen.getHeight() * escalaY);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false; // Restablece el estado
        }
    }
}
