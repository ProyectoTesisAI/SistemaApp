package ec.edu.epn.snai.Utilidades;

import java.util.Random;

public class GeneradorContraseña {

    private int longitud;
    private String contrasena;

    public GeneradorContraseña(int longitud) {
        this.longitud = longitud;
        this.contrasena = this.generarPassword();
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public int getLongitud() {
        return longitud;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String generarPassword() {
        // Para resolver este método utilizamos un array con todos los posibles
        // caracteres que puede tener una contraseña. Posteriormente generaremos
        // números aleatorios entre 0 y la longitud de este array.
        //
        // El carácter asociado a cada posición generada aleatoriamente se irá
        // concatenando a una cadena, inicialmente vacía, que al final del método
        // contendrá la contraseña generada aleatoriamente.

        // Array de posibles caracteres que contendrá la contraseña generada
        final char[] caracteres =
                {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'e', 'h', 'i', 'j', 'l', 'k', 'm',
                        'n', 'o', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                        '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                        '@', '#', '!', '$', '&', '[', ']'};

        // Esta cadena guardará al final del método la contraseña generada aleatoriamente
        String temporal = "";

        Random aleatorio = new Random();

        // Iteramos tantas veces como longitud de caracteres debe tener la contraseña
        for (int i = 0; i < this.longitud; i++) {
            // En cada iteracción a la cadena temporal le asignamos el carácter
            // asociado a la posición (generada aleatoriamente) del array caracteres
            temporal += caracteres[aleatorio.nextInt(caracteres.length)];
        }

        return temporal;
    }


}
