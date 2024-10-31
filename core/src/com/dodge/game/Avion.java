package com.dodge.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Avion extends Vehiculo {

    public Avion(Texture imagen, Sound sonidoChoque) {
        super(imagen, sonidoChoque, 400);
        crear();
    }

    @Override
    public void crear() {
        hitbox.set(100, 200, imagen.getWidth(), imagen.getHeight());
    }

    @Override
    public void actualizarMovimiento(){
        hitbox.y += (float) (velocidad * Math.sin(hitbox.x / 50.0));
    }

    @Override
    public void actualizarEstado(){
        if (herido){
            tiempoHerido--;
            if (tiempoHerido <= 0){
                herido = false;
            }
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(imagen, hitbox.x, hitbox.y);
    }
}
