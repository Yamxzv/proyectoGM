package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class ObstaculoDañino implements IObstaculo {
    private Texture textura;
    private Rectangle area;
    private float escala;
    private TipoObstaculoDañino tipo;

    public enum TipoObstaculoDañino {
        obs1(0.1f, 40, 40),
        obs2(0.3f, 30, 60),
        obs3(0.25f, 60, 10);

        private final float escala;
        private final float width;
        private final float height;

        TipoObstaculoDañino(float escala, float width, float height) {
            this.escala = escala;
            this.width = width;
            this.height = height;
        }
    }

    public ObstaculoDañino(Texture textura, TipoObstaculoDañino tipo) {
        this.textura = textura;
        this.tipo = tipo;
        this.escala = tipo.escala;
        this.area = new Rectangle();
        this.area.x = MathUtils.random(0, 800 - tipo.width);
        this.area.y = 480;
        this.area.width = tipo.width;
        this.area.height = tipo.height;
    }

    @Override
    public void actualizarMovimiento() {
        area.y -= 300 * Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        float drawX = area.x;
        float drawY = area.y;

        if (tipo == TipoObstaculoDañino.obs2) {
            drawX = area.x - ((textura.getWidth() * escala - area.width) / 2);
        } else if (tipo == TipoObstaculoDañino.obs3) {
            drawY = area.y - (textura.getHeight() * escala * 0.2f);
            drawX = area.x - ((textura.getWidth() * escala - area.width) / 2);
        }

        batch.draw(textura, drawX, drawY,
                textura.getWidth() * escala,
                textura.getHeight() * escala);
    }

    @Override
    public Rectangle getArea() {
        return area;
    }

    @Override
    public boolean esDañino() {
        return true;
    }

    @Override
    public void destruir() {
        // No disponemos la textura aquí ya que es manejada por GestorObstaculos
    }

    @Override
    public boolean estaFueraDePantalla() {
        return area.y + area.height < 0;
    }
}
