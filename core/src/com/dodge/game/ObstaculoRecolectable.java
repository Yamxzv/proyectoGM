package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class ObstaculoRecolectable implements IObstaculo {
    private Texture textura; // Textura del objeto recolectable
    private Rectangle area; // Área de colisión del objeto

    public ObstaculoRecolectable(Texture textura) {
        this.textura = textura;
        this.area = new Rectangle();
        // Inicializa la posición aleatoria en el eje X y fija en Y
        this.area.x = MathUtils.random(0, 800 - textura.getWidth());
        this.area.y = 480; // Posición inicial en Y
        this.area.width = textura.getWidth();
        this.area.height = textura.getHeight();
    }

    @Override
    public void actualizarMovimiento() {
        // Desplaza el objeto hacia abajo
        area.y -= 300 * Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        // Dibuja el objeto recolectable en la posición correspondiente
        batch.draw(textura, area.x, area.y);
    }

    @Override
    public Rectangle getArea() {
        return area; // Devuelve el área de colisión
    }

    @Override
    public boolean esDañino() {
        return false; // Indica que este objeto no causa daño
    }

    @Override
    public void destruir() {
        textura.dispose(); // Libera la textura al destruir el objeto
    }
}