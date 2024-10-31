package com.dodge.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Nave extends Vehiculo {

    public Nave(Texture imagen, Sound sonidoChoque) {
        super(imagen, sonidoChoque, 300);
        crear();
    }

    @Override
    public void crear() {
        hitbox.set(100, 100, imagen.getWidth(), imagen.getHeight());
    }

    @Override
    public void actualizarMovimiento(){
        hitbox.x += velocidad;
        hitbox.y += (float) velocidad / 2;
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
