package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class CrearEspacio implements FabricaNiveles {

    @Override
    public Vehiculo crearVehiculo() {
        Vehiculo nave = new Nave(); // Crea un vehículo del tipo nave espacial.
        nave.inicializar(
                new Texture(Gdx.files.internal("nave.png")), // Textura de la nave.
                Gdx.audio.newSound(Gdx.files.internal("choque_nave.mp3")) // Sonido de choque para la nave.
        );
        return nave;
    }

    @Override
    public Texture crearFondo() {
        // Devuelve la textura del fondo para el nivel espacial.
        return new Texture(Gdx.files.internal("fondo_nivel_3.png"));
    }

    @Override
    public GestorObstaculos crearGestorObstaculos() {
        // Inicializa texturas para recolectables específicos del nivel espacio.
        Texture monedaEspacio = new Texture(Gdx.files.internal("moneda3.png"));
        Texture bonusEspacio = new Texture(Gdx.files.internal("bonus.png"));

        // Inicializa texturas para obstáculos espaciales.
        Texture planeta = new Texture(Gdx.files.internal("planeta.png"));
        Texture cometa = new Texture(Gdx.files.internal("cometa.png"));
        Texture asteroide = new Texture(Gdx.files.internal("asteroide.png"));

        // Carga sonidos específicos del nivel espacio.
        Sound coinSound = Gdx.audio.newSound(Gdx.files.internal("sonido_moneda3.mp3"));
        Music musicaEspacio = Gdx.audio.newMusic(Gdx.files.internal("instrumental3.mp3"));

        // Retorna un gestor de obstáculos con los recursos cargados para el nivel espacial.
        return new GestorObstaculos(monedaEspacio, bonusEspacio, planeta, cometa, asteroide,
                coinSound, musicaEspacio);
    }
}
