package com.dodge.game;

public class ScoreManager {

    // Instancia única del Singleton
    private static ScoreManager instance;

    // Atributos para manejar puntaje
    private int currentScore;
    private int highScore;

    // Constructor privado para evitar instanciación externa
    private ScoreManager() {
        currentScore = 0;
        highScore = 0;
    }

    // Metodo estático para obtener la única instancia de ScoreManager
    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    // Métodos para manejar puntajes
    public void addPoints(int points) {
        currentScore += points;
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public void resetCurrentScore() {
        currentScore = 0;
    }

    public void resetHighScore() {
        highScore = 0;
    }
}
