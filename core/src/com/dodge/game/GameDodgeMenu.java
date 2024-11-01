package com.dodge.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDodgeMenu extends Game {

	private SpriteBatch batch;
	private BitmapFont font;
	private int higherScore;

	public void create() {
		batch = new SpriteBatch(); // Inicializa el batch para dibujar
		font = new BitmapFont(); // Fuente Arial predeterminada de libGDX
		this.setScreen(new TutorialScreen(this)); // Cambia a la pantalla de tutorial
	}

	public void render() {
		super.render(); // Renderizado de la pantalla actual
	}

	public void dispose() {
		batch.dispose(); // Libera recursos de batch
		font.dispose(); // Libera recursos de font
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public int getHigherScore() {
		return higherScore;
	}

	public void setHigherScore(int higherScore) {
		this.higherScore = higherScore;
	}

}
