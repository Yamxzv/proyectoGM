package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class CrearTierra implements FabricaNiveles {
    @Override
    public Vehiculo crearVehiculo() {
        Vehiculo auto = new Auto();
        auto.inicializar(
                new Texture(Gdx.files.internal("autorojo1.png")),
                Gdx.audio.newSound(Gdx.files.internal("choque_auto.mp3"))
        );
        return auto;
    }

    @Override
    public Texture crearFondo() {
        return new Texture(Gdx.files.internal("fondo_nivel_1.png"));
    }

    @Override
    public GestorObstaculos crearGestorObstaculos() {
        // Texturas de recolectables
        Texture monedaTierra = new Texture(Gdx.files.internal("moneda.png"));
        Texture bonusTierra = new Texture(Gdx.files.internal("bonus.png"));

        // Texturas de obstáculos terrestres
        Texture roca = new Texture(Gdx.files.internal("roca.png"));
        Texture arbol = new Texture(Gdx.files.internal("arbol.png"));
        Texture hoyo = new Texture(Gdx.files.internal("hoyo.png"));

        // Sonidos del nivel tierra
        Sound coinSound = Gdx.audio.newSound(Gdx.files.internal("moneda.mp3"));
        Music musicaTierra = Gdx.audio.newMusic(Gdx.files.internal("instrumental.mp3"));

        return new GestorObstaculos(monedaTierra, bonusTierra, roca, arbol, hoyo,
                coinSound, musicaTierra);
    }
}