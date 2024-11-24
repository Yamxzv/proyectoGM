package com.dodge.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
	final GameDodgeMenu game; // Referencia al juego principal para manejar pantallas y recursos compartidos
	private final OrthographicCamera camera; // Cámara para ajustar la vista del juego
	private final SpriteBatch batch; // Batch para dibujar texturas y sprites
	private final BitmapFont font; // Fuente para mostrar el texto en pantalla
	private Vehiculo vehiculo; // Vehículo del jugador, cambia según el nivel
	private GestorObstaculos gestorObstaculos; // Administrador de obstáculos y monedas en pantalla
	private Texture fondo; // Textura de fondo que cambia en cada nivel
	private int nivelActual; // Nivel actual del juego
	private final ScoreManager scoreManager; // Usando el Singleton ScoreManager

	public GameScreen(final GameDodgeMenu game) {
		this.game = game;
		this.batch = game.getBatch();
		this.font = game.getFont();
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480); // Configuración inicial de la cámara

		this.nivelActual = 1; // Empieza en el nivel 1
		this.scoreManager = ScoreManager.getInstance();
		scoreManager.resetCurrentScore(); // Resetea el puntaje actual
		inicializarNivel(nivelActual); // Llama a inicializar el nivel actual
	}

	// Inicializa los elementos del nivel según el nivel proporcionado
	private void inicializarNivel(int nivel) {
		// Liberar recursos del nivel anterior
		if (vehiculo != null) {
			scoreManager.addPoints(vehiculo.getPuntos()); // Suma los puntos del vehículo
			vehiculo.destruir(); // Destruye el vehículo del nivel anterior
		}
		if (gestorObstaculos != null) gestorObstaculos.destruir(); // Destruye los obstáculos
		if (fondo != null) fondo.dispose(); // Libera la textura del fondo anterior

		// Usar el patrón Factory para crear el nivel
		FabricaNiveles factory;
		switch (nivel) {
			case 1:
				factory = new CrearTierra(); // Crea el nivel de la Tierra
				break;
			case 2:
				factory = new CrearAire(); // Crea el nivel de Aire
				break;
			case 3:
				factory = new CrearEspacio(); // Crea el nivel Espacial
				break;
			default:
				throw new IllegalArgumentException("Nivel no válido: " + nivel); // Excepción para nivel no válido
		}

		// Crear todos los elementos del nivel usando la fábrica
		vehiculo = factory.crearVehiculo();
		fondo = factory.crearFondo();
		gestorObstaculos = factory.crearGestorObstaculos();

		// Inicializar los elementos
		vehiculo.crear();
		gestorObstaculos.crear();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1); // Limpia la pantalla antes de dibujar
		camera.update(); // Actualiza la cámara
		batch.setProjectionMatrix(camera.combined); // Actualiza la vista del batch

		batch.begin();

		// Dibujar fondo y HUD
		batch.draw(fondo, 0, 0, 800, 480);
		font.setColor(Color.RED);
		font.draw(batch, "Puntos totales: " + scoreManager.getCurrentScore(), 5, 475);
		font.draw(batch, "Vidas : " + vehiculo.getVidas(), 670, 475);
		font.draw(batch, "HighScore : " + scoreManager.getHighScore(), camera.viewportWidth / 2 - 50, 475);

		// Actualizar movimiento y verificar colisiones
		if (!vehiculo.estaHerido()) {
			vehiculo.actualizarMovimiento(); // Actualiza la posición del vehículo
			if (!gestorObstaculos.actualizarMovimiento(vehiculo)) { // Verifica colisiones con obstáculos
				// Si el juego termina, actualizar HighScore y pasar a la pantalla Game Over
				int puntajeFinal = scoreManager.getCurrentScore() + vehiculo.getPuntos();
				if (scoreManager.getHighScore() < puntajeFinal)
					scoreManager.resetHighScore(); // Actualiza el HighScore si es necesario
				game.setScreen(new GameOverScreen(game, puntajeFinal)); // Muestra la pantalla de Game Over
				dispose(); // Libera recursos
			}
		}

		// Dibujar el vehículo y obstáculos
		vehiculo.dibujar(batch);
		gestorObstaculos.actualizarDibujoObjeto(batch); // Actualiza la visualización de los obstáculos
		batch.end();

		verificarCambioNivel(); // Verifica si es necesario cambiar de nivel
	}

	// Verifica si el puntaje actual alcanza el umbral para cambiar de nivel
	private void verificarCambioNivel() {
		int puntajeActual = scoreManager.getCurrentScore();

		if (puntajeActual >= 800 && nivelActual == 1) { // Si el puntaje es mayor a 800 y estamos en el nivel 1
			nivelActual = 2;
			inicializarNivel(nivelActual); // Cambia al nivel 2
		} else if (puntajeActual >= 1600 && nivelActual == 2) { // Si el puntaje es mayor a 1600 y estamos en el nivel 2
			nivelActual = 3;
			inicializarNivel(nivelActual); // Cambia al nivel 3
		}
	}

	@Override
	public void resize(int width, int height) {
		// Método invocado al redimensionar la ventana
	}

	@Override
	public void show() {
		gestorObstaculos.continuar(); // Reanuda obstáculos si estaban pausados
	}

	@Override
	public void hide() {
		// Método invocado al ocultar la pantalla
	}

	@Override
	public void pause() {
		gestorObstaculos.pausar(); // Pausa los obstáculos
		game.setScreen(new PausaScreen(game, this)); // Muestra pantalla de pausa
	}

	@Override
	public void resume() {
		// Método invocado al reanudar la aplicación
	}

	@Override
	public void dispose() {
		// Liberar todos los recursos al salir de la pantalla
		vehiculo.destruir();
		gestorObstaculos.destruir();
		fondo.dispose();
	}
}
