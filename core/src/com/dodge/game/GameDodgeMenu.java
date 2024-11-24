package com.dodge.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDodgeMenu extends Game {

	private SpriteBatch batch; // SpriteBatch para el renderizado de gráficos
	private BitmapFont font; // Fuente para mostrar texto

	public void create() {
		batch = new SpriteBatch(); // Inicializa el batch para dibujar
		font = new BitmapFont(); // Fuente Arial predeterminada de libGDX
		this.setScreen(new TutorialScreen(this)); // Cambia a la pantalla de tutorial
	}

	public void render() {
		super.render(); // Renderiza la pantalla actual
	}

	public void dispose() {
		batch.dispose(); // Libera los recursos del batch
		font.dispose(); // Libera los recursos de la fuente
	}

	public SpriteBatch getBatch() {
		return batch; // Retorna el SpriteBatch
	}

	public BitmapFont getFont() {
		return font; // Retorna la fuente
	}

	public int getHigherScore() {
		return ScoreManager.getInstance().getHighScore(); // Obtiene el puntaje más alto
	}
}
