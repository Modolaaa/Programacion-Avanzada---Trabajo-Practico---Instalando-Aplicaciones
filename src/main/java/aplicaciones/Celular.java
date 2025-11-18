package aplicaciones;

/**
 * Objeto principal que "contiene" la Memoria (Composición).
 */
public class Celular {

    private String modelo;
    private Memoria memoria;

    public Celular(String modelo) {
        this.modelo = modelo;
        // Composición: El Celular es responsable de crear su Memoria
        this.memoria = new Memoria();
    }

    // --- Getters ---

    public Memoria getMemoria() {
        return memoria;
    }

    public String getModelo() {
        return modelo;
    }
}