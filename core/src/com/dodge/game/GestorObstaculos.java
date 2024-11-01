package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GestorObstaculos {
    // Lista de posiciones de los obstáculos
    private Array<Rectangle> obstaculosPos;
    // Lista de tipos de obstáculos (1 = dañino, 2 = recolectable)
    private Array<Integer> obstaculosType;
    // Texturas de los distintos obstáculos
    final private Array<Texture> obstaculosTextures;
    // Lista de texturas activas en pantalla
    private Array<Texture> obstaculosActuales;
    // Tiempo en que se creó el último obstáculo
    private long lastSpawnTime;
    // Textura para los objetos recolectables
    final private Texture recolectable;
    // Sonido de recolección de moneda
    final private Sound coinSound;
    // Música de fondo para el nivel
    final private Music instrumentalMusic;

    public GestorObstaculos(Texture recolectable, Texture roca, Texture arbol, Texture hoyo, Sound ss, Music mm) {
        instrumentalMusic = mm;
        coinSound = ss;
        this.recolectable = recolectable;
        // Inicializa las texturas de los obstáculos
        obstaculosTextures = new Array<>();
        obstaculosTextures.add(roca);
        obstaculosTextures.add(arbol);
        obstaculosTextures.add(hoyo);
        obstaculosActuales = new Array<>();
    }

    public void crear() {
        // Inicializa listas de posiciones, tipos y texturas actuales de obstáculos
        obstaculosPos = new Array<>();
        obstaculosType = new Array<>();
        obstaculosActuales = new Array<>();
        crearObstaculo(); // Genera el primer obstáculo
        instrumentalMusic.setLooping(true);
        instrumentalMusic.play();
    }

    private void crearObstaculo() {
        // Crea un nuevo obstáculo con una posición y tipo aleatorios
        Rectangle hitbox = new Rectangle();
        hitbox.x = MathUtils.random(0, 800 - 64); // Posición horizontal aleatoria
        hitbox.y = 480; // Comienza desde la parte superior de la pantalla

        // Decide el tipo de obstáculo (1 = dañino, 2 = recolectable)
        int tipo = MathUtils.random(1, 10) < 5 ? 1 : 2; // Probabilidad de 50%
        obstaculosType.add(tipo);

        if (tipo == 1) { // Si es un obstáculo dañino
            Texture obstaculo = obstaculosTextures.get(MathUtils.random(obstaculosTextures.size - 1));
            obstaculosActuales.add(obstaculo);

            // Ajusta el tamaño del hitbox según el tipo de obstáculo
            if (obstaculo == obstaculosTextures.get(0)) { // Roca
                hitbox.width = 40;
                hitbox.height = 40;
            } else if (obstaculo == obstaculosTextures.get(1)) { // Árbol
                hitbox.width = 30;
                hitbox.height = 60;
            } else if (obstaculo == obstaculosTextures.get(2)) { // Hoyo
                hitbox.width = 60;
                hitbox.height = 10;
            }
        } else { // Si es un recolectable (moneda)
            obstaculosActuales.add(recolectable);
            hitbox.width = 32;
            hitbox.height = 32;
        }

        // Añade el obstáculo a la lista de posiciones
        obstaculosPos.add(hitbox);
        lastSpawnTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Vehiculo vehiculo) {
        // Crea un nuevo obstáculo si ha pasado suficiente tiempo
        if (TimeUtils.nanoTime() - lastSpawnTime > 100000000) crearObstaculo();

        for (int i = 0; i < obstaculosPos.size; i++) {
            Rectangle hitboxPos = obstaculosPos.get(i);
            hitboxPos.y -= 300 * Gdx.graphics.getDeltaTime(); // Mueve el obstáculo hacia abajo

            // Elimina el obstáculo si sale de la pantalla
            if (hitboxPos.y + 64 < 0) {
                obstaculosPos.removeIndex(i);
                obstaculosType.removeIndex(i);
                obstaculosActuales.removeIndex(i);
            }
            // Verifica colisión con el vehículo
            if (hitboxPos.overlaps(vehiculo.getArea())) {
                if (obstaculosType.get(i) == 1) { // Obstáculo dañino
                    vehiculo.dañar();
                    if (vehiculo.getVidas() <= 0) // Fin del juego si el jugador no tiene vidas
                        return false;
                } else { // Obstáculo recolectable
                    vehiculo.sumarPuntos(10); // Suma puntos al recolectar
                    coinSound.play(); // Reproduce el sonido de la moneda
                }
                // Elimina el obstáculo tras la colisión
                obstaculosPos.removeIndex(i);
                obstaculosType.removeIndex(i);
                obstaculosActuales.removeIndex(i);
            }
        }
        return true; // Continúa el juego si el jugador tiene vidas
    }

    public void actualizarDibujoObjeto(SpriteBatch batch) {
        // Dibuja cada obstáculo en pantalla
        for (int i = 0; i < obstaculosPos.size; i++) {
            Rectangle raindrop = obstaculosPos.get(i);
            Texture obstaculo = obstaculosActuales.get(i);

            float scale = 1.0f; // Escala predeterminada

            if (obstaculosType.get(i) == 2) { // Escala para monedas
                scale = 0.15f;
            } else if (obstaculo == obstaculosTextures.get(0)) { // Escala para roca
                scale = 0.1f;
            } else if (obstaculo == obstaculosTextures.get(1)) { // Escala para árbol
                scale = 0.3f;
                // Ajusta para que el árbol quede centrado en su hitbox
                batch.draw(obstaculo,
                        raindrop.x - ((obstaculo.getWidth() * scale - raindrop.width) / 2),
                        raindrop.y,
                        obstaculo.getWidth() * scale,
                        obstaculo.getHeight() * scale);
                continue;
            } else if (obstaculo == obstaculosTextures.get(2)) { // Escala para hoyo
                scale = 0.25f;
                // Ajuste de posición para el hoyo
                batch.draw(obstaculo,
                        raindrop.x - ((obstaculo.getWidth() * scale - raindrop.width) / 2),
                        raindrop.y - (obstaculo.getHeight() * scale * 0.2f),
                        obstaculo.getWidth() * scale,
                        obstaculo.getHeight() * scale);
                continue;
            }

            // Dibuja el obstáculo en pantalla con la escala correspondiente
            batch.draw(obstaculo, raindrop.x, raindrop.y,
                    obstaculo.getWidth() * scale, obstaculo.getHeight() * scale);
        }
    }

    public void destruir() {
        // Libera todos los recursos al finalizar el nivel
        coinSound.dispose();
        instrumentalMusic.dispose();
        for (Texture texture : obstaculosTextures) texture.dispose();
    }

    public void pausar() {
        // Pausa la música
        instrumentalMusic.pause();
    }

    public void continuar() {
        // Reanuda la música
        instrumentalMusic.play();
    }
}
