package aplicaciones;

public class Aplicacion {

    private String nombre;
    private int peso; // Tamaño en MB
    private String version;
    private String desarrollador;

    public Aplicacion(String nombre, int peso, String version, String desarrollador) {
        this.nombre = nombre;
        this.peso = peso;
        this.version = version;
        this.desarrollador = desarrollador;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getPeso() {
        return peso;
    }

    public String getVersion() {
        return version;
    }

    public String getDesarrollador() {
        return desarrollador;
    }
}