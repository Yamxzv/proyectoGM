package com.dodge.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GestorObstaculos {
    private Array<IObstaculo> obstaculos;
    private final Array<Texture> obstaculosTextures;
    private final Texture recolectableNormal;
    private final Texture recolectableBonus;
    private final Sound coinSound;
    private final Music instrumentalMusic;
    private long lastSpawnTime;

    private final IComportamientoRecolectable comportamientoNormal;
    private final IComportamientoRecolectable comportamientoBonus;

    public GestorObstaculos(Texture recolectableNormal, Texture recolectableBonus,
                            Texture roca, Texture arbol, Texture hoyo, Sound ss, Music mm) {
        this.recolectableNormal = recolectableNormal;
        this.recolectableBonus = recolectableBonus;
        obstaculosTextures = new Array<>();
        obstaculosTextures.add(roca);
        obstaculosTextures.add(arbol);
        obstaculosTextures.add(hoyo);
        coinSound = ss;
        instrumentalMusic = mm;

        comportamientoNormal = new ComportamientoRecolectableNormal();
        comportamientoBonus = new ComportamientoRecolectableBonus();
    }

    public void crear() {
        obstaculos = new Array<>();
        crearObstaculo();
        instrumentalMusic.setLooping(true);
        instrumentalMusic.play();
    }

    private void crearObstaculo() {
        IObstaculo nuevoObstaculo;
        float random = MathUtils.random();

        if (random < comportamientoBonus.getProbabilidadAparicion()) {
            nuevoObstaculo = new ObstaculoRecolectable(recolectableBonus, comportamientoBonus);
        } else if (random < comportamientoNormal.getProbabilidadAparicion()) {
            nuevoObstaculo = new ObstaculoRecolectable(recolectableNormal, comportamientoNormal);
        } else {
            int tipoIndex = MathUtils.random(obstaculosTextures.size - 1);
            Texture texturaObstaculo = obstaculosTextures.get(tipoIndex);
            ObstaculoDañino.TipoObstaculoDañino tipo = ObstaculoDañino.TipoObstaculoDañino.values()[tipoIndex];
            nuevoObstaculo = new ObstaculoDañino(texturaObstaculo, tipo);
        }

        obstaculos.add(nuevoObstaculo);
        lastSpawnTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Vehiculo vehiculo) {
        if (TimeUtils.nanoTime() - lastSpawnTime > 100000000) {
            crearObstaculo();
        }

        for (int i = obstaculos.size - 1; i >= 0; i--) {
            IObstaculo obstaculo = obstaculos.get(i);
            obstaculo.actualizarMovimiento();

            if (obstaculo.estaFueraDePantalla()) {
                obstaculos.removeIndex(i);
                continue;
            }

            if (obstaculo.getArea().overlaps(vehiculo.getArea())) {
                if (obstaculo.esDañino()) {
                    vehiculo.dañar();
                    if (vehiculo.getVidas() <= 0) {
                        return false;
                    }
                } else {
                    ObstaculoRecolectable recolectable = (ObstaculoRecolectable) obstaculo;
                    vehiculo.sumarPuntos(recolectable.getPuntos());
                    coinSound.play();
                }
                obstaculos.removeIndex(i);
            }
        }
        return true;
    }

    public void actualizarDibujoObjeto(SpriteBatch batch) {
        for (IObstaculo obstaculo : obstaculos) {
            obstaculo.dibujar(batch);
        }
    }

    public void destruir() {
        coinSound.dispose();
        instrumentalMusic.dispose();
        for (Texture texture : obstaculosTextures) {
            texture.dispose();
        }
        recolectableNormal.dispose();
        recolectableBonus.dispose();
    }

    public void pausar() {
        instrumentalMusic.pause();
    }

    public void continuar() {
        instrumentalMusic.play();
    }
}