package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Avion extends Vehiculo {

    public Avion(Texture imagen, Sound sonidoChoque) {
        super(imagen, sonidoChoque, 400);
        crear(); // Configura hitbox inicial
    }

    @Override
    public void crear(){
        hitbox.width = 60;
        hitbox.height = 70;
        hitbox.x = (float) 800 / 2 - (float) 64 / 2;
        hitbox.y = 20;
    }

    @Override
    public void actualizarMovimiento() {
        // Movimiento desde teclado
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) hitbox.x -= velocidad * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) hitbox.x += velocidad * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) hitbox.y += velocidad * Gdx.graphics.getDeltaTime(); // Arriba
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) hitbox.y -= velocidad * Gdx.graphics.getDeltaTime(); // Abajo
        // Restricción de bordes
        if(hitbox.x < 0) hitbox.x = 0;
        if(hitbox.x > 800 - 64) hitbox.x = 800 - 64;
        if(hitbox.y < 0) hitbox.y = 0;
        if(hitbox.y > 480 - hitbox.height) hitbox.y = 480 - hitbox.height;
    }

    @Override
    public void actualizarEstado(){
        if (herido){
            tiempoHerido--; // Control de duración de estado "herido"
            if (tiempoHerido <= 0) herido = false;
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        float escalaX = hitbox.width / imagen.getWidth();
        float escalaY = hitbox.height / imagen.getHeight();

        if (!herido) {
            batch.draw(imagen, hitbox.x, hitbox.y, imagen.getWidth() * escalaX, imagen.getHeight() * escalaY); // Dibujo normal
        } else {
            batch.draw(imagen, hitbox.x, hitbox.y + MathUtils.random(-8, 8), imagen.getWidth() * escalaX, imagen.getHeight() * escalaY); // Dibujo con efecto de "temblor"
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }
}
