package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class Vehiculo {
    protected Rectangle hitbox; // Área de colisión
    protected Texture imagen; // Imagen del vehículo
    protected Sound sonidoChoque; // Sonido al chocar
    protected int vidas; // Vidas del vehículo
    protected int puntos; // Puntos acumulados
    protected int velocidadBase; // Velocidad estándar
    protected boolean herido; // Estado de daño
    protected int tiempoHeridoMax; // Tiempo de invulnerabilidad
    protected int tiempoHerido; // Contador de invulnerabilidad

    public final void crear() {
        inicializarEstado(); // Inicializa las variables
        definirVelocidad(); // Define la velocidad
        configurarHitbox(); // Configura la hitbox
        configurarPosicionInicial(); // Ubica al vehículo
    }

    public final void inicializar(Texture imagen, Sound soundChoque) {
        this.imagen = imagen;
        this.sonidoChoque = soundChoque;
        crear(); // Llama al flujo de inicialización
    }

    private void inicializarEstado() {
        vidas = 3; // Inicializa vidas
        puntos = 0; // Inicializa puntos
        tiempoHeridoMax = 50; // Tiempo invulnerable
        tiempoHerido = 0; // Sin invulnerabilidad
    }

    protected abstract void definirVelocidad(); // Define velocidad
    protected abstract void configurarHitbox(); // Configura hitbox
    protected abstract void configurarPosicionInicial(); // Posición inicial

    public void actualizarMovimiento() {
        actualizarEstadoHerido(); // Actualiza si está herido
        actualizarPosicion(); // Movimiento
        restriccionBordes(); // Mantiene dentro del área
    }

    protected void actualizarPosicion() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) hitbox.x -= velocidadBase * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) hitbox.x += velocidadBase * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) hitbox.y += velocidadBase * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) hitbox.y -= velocidadBase * Gdx.graphics.getDeltaTime();
    }

    protected void restriccionBordes() {
        if(hitbox.x < 0) hitbox.x = 0; // Borde izquierdo
        if(hitbox.x > 800 - 64) hitbox.x = 800 - 64; // Borde derecho
        if(hitbox.y < 0) hitbox.y = 0; // Borde inferior
        if(hitbox.y > 480 - hitbox.height) hitbox.y = 480 - hitbox.height; // Borde superior
    }

    public void dibujar(SpriteBatch batch) {
        float escalaX = hitbox.width / imagen.getWidth();
        float escalaY = hitbox.height / imagen.getHeight();

        if (!herido) {
            batch.draw(imagen, hitbox.x, hitbox.y, imagen.getWidth() * escalaX, imagen.getHeight() * escalaY);
        } else {
            batch.draw(imagen, hitbox.x, hitbox.y + MathUtils.random(-8, 8), imagen.getWidth() * escalaX, imagen.getHeight() * escalaY);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }

    public void dañar() {
        vidas--; // Reduce vidas
        herido = true; // Activa estado herido
        tiempoHerido = tiempoHeridoMax; // Invulnerabilidad
        sonidoChoque.play(); // Reproduce sonido
    }

    public void destruir() {
        if (imagen != null) {
            imagen.dispose(); // Libera recursos
        }
    }

    public void actualizarEstadoHerido() {
        if (herido) {
            tiempoHerido--; // Reduce contador
            if (tiempoHerido <= 0) herido = false; // Finaliza invulnerabilidad
        }
    }

    public boolean estaHerido() {
        return herido; // Retorna estado
    }

    public Rectangle getArea() {
        return hitbox; // Retorna hitbox
    }

    public int getVidas() {
        return vidas; // Retorna vidas
    }

    public int getPuntos() {
        return puntos; // Retorna puntos
    }

    public void sumarPuntos(int puntos) {
        ScoreManager.getInstance().addPoints(puntos); // Suma puntos globales
    }
}
