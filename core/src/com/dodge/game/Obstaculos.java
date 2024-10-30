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

public class Obstaculos {
	private Array<Rectangle> rainDropsPos;
	private Array<Integer> rainDropsType;
	private Array<Texture> obstaculosTextures;
	private Array<Texture> obstaculosActuales;
	private long lastDropTime;
	private Texture gotaBuena;
	private Sound coinSound;
	private Music instrumentalMusic;

	public Obstaculos(Texture gotaBuena, Texture roca, Texture arbol, Texture hoyo, Sound ss, Music mm) {
		instrumentalMusic = mm;
		coinSound = ss;
		this.gotaBuena = gotaBuena;
		obstaculosTextures = new Array<>();
		obstaculosTextures.add(roca);
		obstaculosTextures.add(arbol);
		obstaculosTextures.add(hoyo);
		obstaculosActuales = new Array<>();
	}

	public void crear() {
		rainDropsPos = new Array<>();
		rainDropsType = new Array<>();
		obstaculosActuales = new Array<>();
		crearGotaDeLluvia();
		instrumentalMusic.setLooping(true);
		instrumentalMusic.play();
	}

	private void crearGotaDeLluvia() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64);
		raindrop.y = 480;

		// Decide el tipo de obstáculo (1 = dañino, 2 = recolectable)
		int tipo = MathUtils.random(1, 10) < 5 ? 1 : 2;
		rainDropsType.add(tipo);

		if (tipo == 1) { // Obstáculo dañino
			Texture obstaculo = obstaculosTextures.get(MathUtils.random(obstaculosTextures.size - 1));
			obstaculosActuales.add(obstaculo);

			// Define el tamaño de hitbox para cada obstáculo
			if (obstaculo == obstaculosTextures.get(0)) { // roca
				float scale = 0.1f;
				raindrop.width = obstaculo.getWidth() * scale;
				raindrop.height = obstaculo.getHeight() * scale;
				/*
				raindrop.width = 60;
				raindrop.height = 40;

				 */
			} else if (obstaculo == obstaculosTextures.get(1)) { // árbol
				raindrop.width = 20;
				raindrop.height = 28;
			} else if (obstaculo == obstaculosTextures.get(2)) { // hoyo
				raindrop.width = 60;
				raindrop.height = 1;
			}
		} else {
			// Dimensiones estándar para objetos recolectables
			obstaculosActuales.add(gotaBuena);
			raindrop.width = 32;
			raindrop.height = 32;
		}

		rainDropsPos.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	public boolean actualizarMovimiento(Auto tarro) {
		if (TimeUtils.nanoTime() - lastDropTime > 100000000) crearGotaDeLluvia();

		for (int i = 0; i < rainDropsPos.size; i++) {
			Rectangle raindrop = rainDropsPos.get(i);
			raindrop.y -= 300 * Gdx.graphics.getDeltaTime();

			// Remueve gota fuera de pantalla
			if (raindrop.y + 64 < 0) {
				rainDropsPos.removeIndex(i);
				rainDropsType.removeIndex(i);
				obstaculosActuales.removeIndex(i);
			}
			// Verifica colisión
			if (raindrop.overlaps(tarro.getArea())) {
				if (rainDropsType.get(i) == 1) { // Daño
					tarro.dañar();
					if (tarro.getVidas() <= 0)
						return false;
				} else { // Recolección
					tarro.sumarPuntos(10);
					coinSound.play();
				}
				rainDropsPos.removeIndex(i);
				rainDropsType.removeIndex(i);
				obstaculosActuales.removeIndex(i);
			}
		}
		return true;
	}

	public void actualizarDibujoLluvia(SpriteBatch batch) {
		for (int i = 0; i < rainDropsPos.size; i++) {
			Rectangle raindrop = rainDropsPos.get(i);
			Texture obstaculo = obstaculosActuales.get(i);

			float scale = 1.0f; // Escala por defecto

			if (rainDropsType.get(i) == 2) { // Moneda (recolectable)
				scale = 0.15f;
			} else if (obstaculo == obstaculosTextures.get(0)) { // Roca
				scale = 0.1f;
			} else if (obstaculo == obstaculosTextures.get(1)) { // Árbol
				scale = 0.3f;

				// Dibuja árbol centrado en la hitbox
				batch.draw(obstaculo,
						raindrop.x - ((obstaculo.getWidth() * scale - raindrop.width) / 2),
						raindrop.y,
						obstaculo.getWidth() * scale,
						obstaculo.getHeight() * scale);
				continue;
			} else if (obstaculo == obstaculosTextures.get(2)) { // Hoyo
				scale = 0.25f;

				// Ajuste de posición y centrado
				batch.draw(obstaculo,
						raindrop.x - ((obstaculo.getWidth() * scale - raindrop.width) / 2),
						raindrop.y - (obstaculo.getHeight() * scale * 0.2f), // Ajuste vertical
						obstaculo.getWidth() * scale,
						obstaculo.getHeight() * scale);
				continue;
			}

			// Dibuja obstáculo con tamaño ajustado
			batch.draw(obstaculo, raindrop.x, raindrop.y,
					obstaculo.getWidth() * scale, obstaculo.getHeight() * scale);
		}
	}

	public void destruir() {
		coinSound.dispose();
		instrumentalMusic.dispose();
		for (Texture texture : obstaculosTextures) texture.dispose();
	}

	public void pausar() {
		instrumentalMusic.pause();
	}

	public void continuar() {
		instrumentalMusic.play();
	}
}