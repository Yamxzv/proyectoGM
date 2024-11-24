package com.dodge.game;

import com.badlogic.gdx.graphics.Texture;

public interface FabricaNiveles {
    // Método para crear el vehículo asociado al nivel.
    Vehiculo crearVehiculo();

    // Método para proporcionar la textura del fondo del nivel.
    Texture crearFondo();

    // Método para crear y configurar el gestor de obstáculos del nivel.
    GestorObstaculos crearGestorObstaculos();
}
