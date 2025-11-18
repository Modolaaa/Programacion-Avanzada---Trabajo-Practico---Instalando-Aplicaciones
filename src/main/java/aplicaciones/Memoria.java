package aplicaciones;


import java.util.ArrayList;
import java.util.List;

/**
 * Modela el almacenamiento del celular, manteniendo la lista
 * ordenada de aplicaciones.
 */
public class Memoria {

    private int capacidadTotal; // En este problema, es la suma de los pesos.
    private List<Aplicacion> aplicacionesInstaladas;

    public Memoria() {
        // Usamos ArrayList para mantener el orden de inserción (consecutivo)
        this.aplicacionesInstaladas = new ArrayList<>();
        this.capacidadTotal = 0;
    }

    /**
     * Añade una app al final de la lista.
     * @param app La aplicación a instalar.
     */
    public void agregarAplicacion(Aplicacion app) {
        this.aplicacionesInstaladas.add(app);
        this.capacidadTotal += app.getPeso();
    }

    /**
     * Elimina un rango de aplicaciones.
     * @param indiceInicio El índice de la primera app a eliminar.
     * @param cantidad El número de apps a eliminar.
     */
    public void eliminarAplicaciones(int indiceInicio, int cantidad) {
        if (indiceInicio < 0 || indiceInicio + cantidad > aplicacionesInstaladas.size()) {
            System.err.println("Error: Rango de desinstalación inválido.");
            return;
        }

        for (int i = 0; i < cantidad; i++) {
            // remove(indice) es más eficiente en ArrayList si se remueve
            // desde el final, pero para este caso, remove(indice) es claro.
            Aplicacion appEliminada = this.aplicacionesInstaladas.remove(indiceInicio);
            this.capacidadTotal -= appEliminada.getPeso();
        }
    }

    // --- Getters ---

    public List<Aplicacion> getAplicacionesInstaladas() {
        return aplicacionesInstaladas;
    }

    public int getCapacidadTotal() {
        return capacidadTotal;
    }
}