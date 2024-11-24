package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class CrearAire implements FabricaNiveles {

    @Override
    public Vehiculo crearVehiculo() {
        Vehiculo avion = new Avion(); // Crea un vehículo del tipo avión.
        avion.inicializar(
                new Texture(Gdx.files.internal("avion.png")), // Textura del avión.
                Gdx.audio.newSound(Gdx.files.internal("choque_avion.mp3")) // Sonido de choque específico.
        );
        return avion;
    }

    @Override
    public Texture crearFondo() {
        // Devuelve la textura del fondo para el nivel aéreo.
        return new Texture(Gdx.files.internal("fondo_nivel_2.png"));
    }

    @Override
    public GestorObstaculos crearGestorObstaculos() {
        // Inicializa texturas para recolectables específicos del nivel aire.
        Texture monedaAire = new Texture(Gdx.files.internal("moneda2.png"));
        Texture bonusAire = new Texture(Gdx.files.internal("bonus.png"));

        // Inicializa texturas para obstáculos específicos del nivel aire.
        Texture nube = new Texture(Gdx.files.internal("nube.png"));
        Texture relampago = new Texture(Gdx.files.internal("relampago.png"));
        Texture pajaro = new Texture(Gdx.files.internal("pajaro.png"));

        // Carga sonidos específicos del nivel aire.
        Sound coinSound = Gdx.audio.newSound(Gdx.files.internal("sonido_moneda2.mp3"));
        Music musicaAire = Gdx.audio.newMusic(Gdx.files.internal("instrumental2.mp3"));

        // Retorna un gestor de obstáculos con los recursos cargados.
        return new GestorObstaculos(monedaAire, bonusAire, nube, relampago, pajaro,
                coinSound, musicaAire);
    }
}
