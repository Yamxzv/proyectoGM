package com.dodge.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class Vehiculo {
    protected Rectangle hitbox; // Área de colisión del vehículo
    protected Texture imagen; // Textura del vehículo
    protected Sound sonidoChoque; // Sonido al chocar
    protected int vidas; // Número de vidas del vehículo
    protected int puntos; // Puntos acumulados
    protected int velocidadBase; // Velocidad de movimiento del vehículo
    protected boolean herido; // Estado de daño del vehículo
    protected int tiempoHeridoMax; // Tiempo máximo de daño
    protected int tiempoHerido; // Tiempo restante de estado herido

    // Template method que define el esqueleto del algoritmo
    public final void crear(){
        inicializarEstado();
        definirVelocidad();
        configurarHitbox();
        configurarPosicionInicial();
    }

    // Método template para inicializar el vehículo
    public final void inicializar(Texture imagen, Sound soundChoque) {
        this.imagen = imagen;
        this.sonidoChoque = soundChoque;
        crear();
    }

    private void inicializarEstado(){
        vidas = 3; // Inicializa las vidas
        puntos = 0; // Inicializa los puntos
        tiempoHeridoMax = 50; // Define el tiempo máximo de herido
        tiempoHerido = 0; // Inicializa el tiempo herido
    }

    // Métodos abstractos que deben implementar las subclases
    protected abstract void definirVelocidad();
    protected abstract void configurarHitbox();
    protected abstract void configurarPosicionInicial();
    public abstract void actualizarMovimiento();

    // Métodos concretos comunes a todos los vehículos

    // Método para dibujar el vehículo
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

    // Método para dañar el vehículo
    public void dañar() {
        vidas--; // Reduce las vidas
        herido = true; // Establece el estado de herido
        tiempoHerido = tiempoHeridoMax; // Reinicia el temporizador de herido
        sonidoChoque.play(); // Reproduce el sonido de choque
    }

    // Método para liberar recursos
    public void destruir() {
        if (imagen != null) {
            imagen.dispose(); // Libera la textura del vehículo
        }
    }

    public void actualizarEstadoHerido(){
        if (herido){
            tiempoHerido--; // Control de duración de estado "herido"
            if (tiempoHerido <= 0) herido = false;
        }
    }

    // Getters y setters

    public boolean estaHerido() {
        return herido; // Retorna si el vehículo está herido
    }

    public Rectangle getArea() {
        return hitbox; // Retorna el área de colisión
    }

    public int getVidas() {
        return vidas; // Retorna el número de vidas
    }

    public int getPuntos() {
        return puntos; // Retorna los puntos acumulados
    }

    public void sumarPuntos(int puntos) {
        ScoreManager.getInstance().addPoints(puntos);
    }

}
