package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class CrearAire implements FabricaNiveles {
    @Override
    public Vehiculo crearVehiculo() {
        Vehiculo avion = new Avion();
        avion.inicializar(
                new Texture(Gdx.files.internal("avion.png")),
                Gdx.audio.newSound(Gdx.files.internal("choque_avion.mp3"))
        );
        return avion;
    }

    @Override
    public Texture crearFondo() {
        return new Texture(Gdx.files.internal("fondo_nivel_2.png"));
    }

    @Override
    public GestorObstaculos crearGestorObstaculos() {
        // Texturas de recolectables aéreos
        Texture monedaAire = new Texture(Gdx.files.internal("moneda2.png"));
        Texture bonusAire = new Texture(Gdx.files.internal("bonus.png"));

        // Texturas de obstáculos aéreos
        Texture nube = new Texture(Gdx.files.internal("nube.png"));
        Texture relampago = new Texture(Gdx.files.internal("relampago.png"));
        Texture pajaro = new Texture(Gdx.files.internal("pajaro.png"));

        // Sonidos del nivel aire
        Sound coinSound = Gdx.audio.newSound(Gdx.files.internal("sonido_moneda2.mp3"));
        Music musicaAire = Gdx.audio.newMusic(Gdx.files.internal("instrumental2.mp3"));

        return new GestorObstaculos(monedaAire, bonusAire, nube, relampago, pajaro,
                coinSound, musicaAire);
    }
}