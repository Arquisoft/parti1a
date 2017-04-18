package utils;

public class ThreadUtil {
	/*
	 * Clase creada para ahorrarnos el colocar un try catch cada vez que queremos hacer una espera para que las pruebas esperen a que se cargue la pagina
	 */
	public static void wait(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		}

		catch (InterruptedException ex) {

		}
	}
}
