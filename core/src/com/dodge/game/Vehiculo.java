package com.dodge.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Vehiculo {
    protected Rectangle hitbox; // Área de colisión del vehículo
    protected Texture imagen; // Textura del vehículo
    protected Sound sonidoChoque; // Sonido al chocar
    protected int vidas; // Número de vidas del vehículo
    protected int puntos; // Puntos acumulados
    protected int velocidad; // Velocidad de movimiento del vehículo
    protected boolean herido; // Estado de daño del vehículo
    final protected int tiempoHeridoMax; // Tiempo máximo de daño
    protected int tiempoHerido; // Tiempo restante de estado herido

    // Constructor que inicializa las propiedades del vehículo
    public Vehiculo(Texture imagen, Sound sonidoChoque, int velocidad) {
        this.imagen = imagen;
        this.sonidoChoque = sonidoChoque;
        this.velocidad = velocidad;
        vidas = 3; // Inicializa las vidas
        puntos = 0; // Inicializa los puntos
        tiempoHeridoMax = 50; // Define el tiempo máximo de herido
        tiempoHerido = 0; // Inicializa el tiempo herido
        hitbox = new Rectangle(); // Crea el rectángulo de colisión
    }

    // Métodos abstractos que cada tipo de vehículo deberá implementar
    public abstract void crear();
    public abstract void actualizarMovimiento();
    public abstract void actualizarEstado();
    public abstract void dibujar(SpriteBatch batch);

    // Método para dañar el vehículo
    public void dañar() {
        vidas--; // Reduce las vidas
        herido = true; // Establece el estado de herido
        tiempoHerido = tiempoHeridoMax; // Reinicia el temporizador de herido
        sonidoChoque.play(); // Reproduce el sonido de choque
    }

    // Método para sumar puntos al vehículo
    public void sumarPuntos(int puntos) {
        this.puntos += puntos; // Aumenta los puntos acumulados
    }

    // Métodos de acceso para obtener información del vehículo
    public int getPuntos() {
        return puntos; // Retorna los puntos acumulados
    }

    public int getVidas() {
        return vidas; // Retorna el número de vidas
    }

    public Rectangle getArea() {
        return hitbox; // Retorna el área de colisión
    }

    public boolean estaHerido() {
        return herido; // Retorna si el vehículo está herido
    }

    // Método para liberar recursos
    public void destruir() {
        if (imagen != null) {
            imagen.dispose(); // Libera la textura del vehículo
        }
    }
}
