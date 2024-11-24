package com.dodge.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GestorObstaculos {
    private Array<IObstaculo> obstaculos; // Lista de obstáculos en pantalla
    private final Array<Texture> obstaculosTextures; // Texturas de obstáculos dañinos
    private final Texture recolectableNormal; // Textura para el recolectable normal
    private final Texture recolectableBonus; // Textura para el recolectable bonus
    private final Sound coinSound; // Sonido para la recolección de monedas
    private final Music instrumentalMusic; // Música de fondo
    private long lastSpawnTime; // Tiempo del último obstáculo creado

    private final IComportamientoRecolectable comportamientoNormal; // Comportamiento del recolectable normal
    private final IComportamientoRecolectable comportamientoBonus; // Comportamiento del recolectable bonus

    // Constructor que inicializa los recursos de obstáculos y sonidos
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

        // Inicializa los comportamientos de los recolectables
        comportamientoNormal = new ComportamientoRecolectableNormal();
        comportamientoBonus = new ComportamientoRecolectableBonus();
    }

    // Método que inicializa la lista de obstáculos y comienza a reproducir música
    public void crear() {
        obstaculos = new Array<>(); // Crea la lista de obstáculos
        crearObstaculo(); // Crea el primer obstáculo
        instrumentalMusic.setLooping(true); // Reproduce la música de fondo en bucle
        instrumentalMusic.play(); // Comienza la música
    }

    // Método que crea un nuevo obstáculo (ya sea recolectable o dañino)
    private void crearObstaculo() {
        IObstaculo nuevoObstaculo;
        float random = MathUtils.random(); // Genera un valor aleatorio

        // Se decide qué tipo de obstáculo se crea según probabilidades
        if (random < comportamientoBonus.getProbabilidadAparicion()) {
            nuevoObstaculo = new ObstaculoRecolectable(recolectableBonus, comportamientoBonus);
        } else if (random < comportamientoNormal.getProbabilidadAparicion()) {
            nuevoObstaculo = new ObstaculoRecolectable(recolectableNormal, comportamientoNormal);
        } else {
            // Si no es un recolectable, crea un obstáculo dañino aleatorio
            int tipoIndex = MathUtils.random(obstaculosTextures.size - 1);
            Texture texturaObstaculo = obstaculosTextures.get(tipoIndex);
            ObstaculoDañino.TipoObstaculoDañino tipo = ObstaculoDañino.TipoObstaculoDañino.values()[tipoIndex];
            nuevoObstaculo = new ObstaculoDañino(texturaObstaculo, tipo);
        }

        obstaculos.add(nuevoObstaculo); // Añade el obstáculo a la lista
        lastSpawnTime = TimeUtils.nanoTime(); // Guarda el tiempo de creación del obstáculo
    }

    // Actualiza el movimiento de los obstáculos y verifica las colisiones
    public boolean actualizarMovimiento(Vehiculo vehiculo) {
        // Si ha pasado un tiempo suficientemente largo, crea un nuevo obstáculo
        if (TimeUtils.nanoTime() - lastSpawnTime > 100000000) {
            crearObstaculo();
        }

        // Recorre todos los obstáculos para actualizar su movimiento
        for (int i = obstaculos.size - 1; i >= 0; i--) {
            IObstaculo obstaculo = obstaculos.get(i);
            obstaculo.actualizarMovimiento();

            // Si el obstáculo ha salido de la pantalla, lo elimina
            if (obstaculo.estaFueraDePantalla()) {
                obstaculos.removeIndex(i);
                continue;
            }

            // Verifica si el obstáculo colisiona con el vehículo
            if (obstaculo.getArea().overlaps(vehiculo.getArea())) {
                if (obstaculo.esDañino()) {
                    // Si es un obstáculo dañino, daña al vehículo
                    vehiculo.dañar();
                    if (vehiculo.getVidas() <= 0) {
                        return false; // Si el vehículo no tiene vidas, termina el juego
                    }
                } else {
                    // Si es un recolectable, suma puntos al vehículo
                    ObstaculoRecolectable recolectable = (ObstaculoRecolectable) obstaculo;
                    vehiculo.sumarPuntos(recolectable.getPuntos());
                    coinSound.play(); // Reproduce el sonido de la moneda
                }
                obstaculos.removeIndex(i); // Elimina el obstáculo después de interactuar
            }
        }
        return true; // Si no se terminó el juego, devuelve true
    }

    // Actualiza el dibujo de los obstáculos en pantalla
    public void actualizarDibujoObjeto(SpriteBatch batch) {
        for (IObstaculo obstaculo : obstaculos) {
            obstaculo.dibujar(batch); // Dibuja cada obstáculo
        }
    }

    // Libera los recursos usados (texturas, sonidos, música) cuando se destruye el gestor
    public void destruir() {
        coinSound.dispose(); // Libera el recurso de sonido
        instrumentalMusic.dispose(); // Libera el recurso de música
        for (Texture texture : obstaculosTextures) {
            texture.dispose(); // Libera las texturas de los obstáculos
        }
        recolectableNormal.dispose(); // Libera la textura del recolectable normal
        recolectableBonus.dispose(); // Libera la textura del recolectable bonus
    }

    // Pausa la música de fondo
    public void pausar() {
        instrumentalMusic.pause();
    }

    // Reanuda la música de fondo
    public void continuar() {
        instrumentalMusic.play();
    }
}
