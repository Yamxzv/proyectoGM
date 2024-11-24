package com.dodge.game;

import com.badlogic.gdx.graphics.Texture;

public interface FabricaNiveles {
    Vehiculo crearVehiculo();
    Texture crearFondo();
    GestorObstaculos crearGestorObstaculos();
}