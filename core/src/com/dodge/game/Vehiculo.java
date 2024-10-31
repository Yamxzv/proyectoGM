package com.dodge.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Vehiculo {
    protected Rectangle hitbox;
    protected Texture imagen;
    protected Sound sonidoChoque;
    protected int vidas;
    protected int puntos;
    protected int velocidad;
    protected boolean herido;
    final protected int tiempoHeridoMax;
    protected int tiempoHerido;

    public Vehiculo(Texture imagen, Sound sonidoChoque, int velocidad) {
        this.imagen = imagen;
        this.sonidoChoque = sonidoChoque;
        this.velocidad = velocidad;
        vidas = 3;
        puntos = 0;
        tiempoHeridoMax = 50;
        tiempoHerido = 0;
        hitbox = new Rectangle();
    }

    // Métodos abstractos que cada tipo de vehículo deberá implementar
    public abstract void crear();
    public abstract void actualizarMovimiento();
    public abstract void actualizarEstado();
    public abstract void dibujar(SpriteBatch batch);

    // Métodos comunes
    public void dañar() {
        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        sonidoChoque.play();
    }

    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getVidas() {
        return vidas;
    }

    public Rectangle getArea() {
        return hitbox;
    }

    public boolean estaHerido() {
        return herido;
    }

    public void destruir() {
        if (imagen != null) {
            imagen.dispose();
        }
    }

}
