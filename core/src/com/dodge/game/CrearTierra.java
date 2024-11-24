package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class CrearTierra implements FabricaNiveles {

    @Override
    public Vehiculo crearVehiculo() {
        Vehiculo auto = new Auto(); // Crea un vehículo del tipo auto.
        auto.inicializar(
                new Texture(Gdx.files.internal("autorojo1.png")), // Textura para el auto.
                Gdx.audio.newSound(Gdx.files.internal("choque_auto.mp3")) // Sonido de choque del auto.
        );
        return auto;
    }

    @Override
    public Texture crearFondo() {
        // Devuelve la textura del fondo para el nivel tierra.
        return new Texture(Gdx.files.internal("fondo_nivel_1.png"));
    }

    @Override
    public GestorObstaculos crearGestorObstaculos() {
        // Inicializa texturas para recolectables específicos del nivel tierra.
        Texture monedaTierra = new Texture(Gdx.files.internal("moneda.png"));
        Texture bonusTierra = new Texture(Gdx.files.internal("bonus.png"));

        // Inicializa texturas para obstáculos terrestres.
        Texture roca = new Texture(Gdx.files.internal("roca.png"));
        Texture arbol = new Texture(Gdx.files.internal("arbol.png"));
        Texture hoyo = new Texture(Gdx.files.internal("hoyo.png"));

        // Carga sonidos específicos del nivel tierra.
        Sound coinSound = Gdx.audio.newSound(Gdx.files.internal("moneda.mp3"));
        Music musicaTierra = Gdx.audio.newMusic(Gdx.files.internal("instrumental.mp3"));

        // Retorna un gestor de obstáculos con los recursos cargados para el nivel tierra.
        return new GestorObstaculos(monedaTierra, bonusTierra, roca, arbol, hoyo,
                coinSound, musicaTierra);
    }
}
