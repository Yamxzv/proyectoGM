package com.dodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class ObstaculoRecolectable implements IObstaculo {
    private Texture textura;
    private Rectangle area;
    private static final float ESCALA = 0.15f;
    private IComportamientoRecolectable comportamiento;

    public ObstaculoRecolectable(Texture textura, IComportamientoRecolectable comportamiento) {
        this.textura = textura;
        this.comportamiento = comportamiento;
        this.area = new Rectangle();
        this.area.x = MathUtils.random(0, 800 - 32);
        this.area.y = 480;
        this.area.width = 32;
        this.area.height = 32;
    }

    public int getPuntos() {
        return comportamiento.getPuntos();
    }

    @Override
    public void actualizarMovimiento() {
        area.y -= 300 * Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y,
                textura.getWidth() * ESCALA,
                textura.getHeight() * ESCALA);
    }

    @Override
    public Rectangle getArea() {
        return area;
    }

    @Override
    public boolean esDañino() {
        return false;
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