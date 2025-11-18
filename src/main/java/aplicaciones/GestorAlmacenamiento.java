package aplicaciones;


import java.util.List;

/**
 * Clase de servicio que contiene la lógica de negocio.
 * Utiliza Inyección de Dependencias (recibe el Celular)
 * para facilitar las pruebas (testing).
 */
public class GestorAlmacenamiento {

    private Celular celular;

    /**
     * Constructor para Inyección de Dependencias.
     * @param celular El celular a gestionar.
     */
    public GestorAlmacenamiento(Celular celular) {
        this.celular = celular;
    }

    /**
     * Orquesta el cálculo para encontrar el mínimo de apps a desinstalar.
     * @param espacioRequerido El espacio consecutivo en MB necesario.
     * @return El número mínimo de apps, o -1 si no es posible.
     */
    public int calcularMinimoAppsADesinstalar(int espacioRequerido) {
        List<Aplicacion> apps = this.celular.getMemoria().getAplicacionesInstaladas();
        int cantApps = apps.size();

        if (cantApps == 0) {
            return -1; // No hay apps para desinstalar
        }

        // 1. Convertir lista de objetos a arreglo de pesos
        int[] pesosApps = new int[cantApps];
        for (int i = 0; i < cantApps; i++) {
            pesosApps[i] = apps.get(i).getPeso();
        }

        // 2. Llamar al algoritmo de sliding window
        return this.algoritmoInstalandoAplicaciones(cantApps, espacioRequerido, pesosApps);
    }

    /**
     * Algoritmo de Sliding Window (Ventana Deslizante)
     * Resuelve el problema en tiempo O(n) y espacio O(1).
     */
    private int algoritmoInstalandoAplicaciones(int cantApps, int tamApp, int[] apps) {
        int izquierda = 0, suma = 0;
        int minApps = Integer.MAX_VALUE;

        for (int derecha = 0; derecha < cantApps; derecha++) {
            suma += apps[derecha];  // 1. Expandimos la ventana

            // 2. Intentamos reducir la ventana desde la izquierda
            //    mientras cumplamos la condición (suma >= tamApp)
            while (suma >= tamApp) {
                // 3. Actualizamos el mínimo encontrado
                minApps = Math.min(minApps, derecha - izquierda + 1);

                // 4. Deslizamos la ventana
                suma -= apps[izquierda];
                izquierda++;
            }
        }

        // 5. Devolvemos el resultado
        return minApps == Integer.MAX_VALUE ? -1 : minApps;
    }

    // --- Otros métodos del diseño ---

    public void instalar(Aplicacion app) {
        this.celular.getMemoria().agregarAplicacion(app);
    }

    public void desinstalar(int indiceInicio, int cantidad) {
        this.celular.getMemoria().eliminarAplicaciones(indiceInicio, cantidad);
    }
}