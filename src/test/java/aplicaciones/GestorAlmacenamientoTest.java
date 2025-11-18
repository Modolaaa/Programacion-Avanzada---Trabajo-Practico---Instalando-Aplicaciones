package aplicaciones;

// Importaciones de JUnit 5
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// Importaciones de Mockito
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// Importaciones de Clases del Proyecto

import aplicaciones.Aplicacion;
import aplicaciones.Celular;
import aplicaciones.GestorAlmacenamiento;
import aplicaciones.Memoria;

// Importaciones de Java
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GestorAlmacenamientoTest {

    private GestorAlmacenamiento gestor; // SUT (System Under Test)
    private Celular mockCelular;
    private Memoria mockMemoria;

    /**
     * Método helper para simular (mockear) una Aplicacion
     * con un peso específico.
     */
    private Aplicacion mockApp(int peso) {
        // Creamos un mock de Aplicacion
        Aplicacion app = mock(Aplicacion.class);
        // Definimos que cuando se llame a getPeso(), devuelva el peso
        when(app.getPeso()).thenReturn(peso);
        return app;
    }

    @BeforeEach
    void setUp() {
        // 1. Crear Mocks
        mockCelular = mock(Celular.class);
        mockMemoria = mock(Memoria.class);

        // 2. Inyectar dependencias
        // Creamos el gestor real, pero le pasamos el celular "falso" (mock)
        gestor = new GestorAlmacenamiento(mockCelular);

        // 3. Definir comportamiento de los mocks
        // Cuando el gestor llame a celular.getMemoria(),
        // le devolveremos la memoria "falsa" (mock)
        when(mockCelular.getMemoria()).thenReturn(mockMemoria);
    }

    @Test
    @DisplayName("Debe resolver el caso de ejemplo del PDF (100MB -> 3 apps)")
    void testCasoEjemploPDF() {
        // 1. Arrange (Preparar)
        int espacioRequerido = 100;
        // Creamos la lista de aplicaciones simuladas
        List<Aplicacion> apps = Stream.of(42, 2, 50, 10, 1, 50, 30, 24, 18, 23)
                .map(this::mockApp)
                .collect(Collectors.toList());

        // Cuando se pida la lista de apps, devolvemos la lista simulada
        when(mockMemoria.getAplicacionesInstaladas()).thenReturn(apps);

        // 2. Act (Ejecutar)
        int resultado = gestor.calcularMinimoAppsADesinstalar(espacioRequerido);

        // 3. Assert (Verificar)
        assertEquals(3, resultado, "El resultado debe ser 3 (50 + 30 + 24)");
    }

    @Test
    @DisplayName("Debe devolver -1 si el espacio es inalcanzable")
    void testCasoImposible() {
        // 1. Arrange
        int espacioRequerido = 1000;
        List<Aplicacion> apps = Stream.of(10, 20, 30, 10)
                .map(this::mockApp)
                .collect(Collectors.toList());

        when(mockMemoria.getAplicacionesInstaladas()).thenReturn(apps);

        // 2. Act
        int resultado = gestor.calcularMinimoAppsADesinstalar(espacioRequerido);

        // 3. Assert
        assertEquals(-1, resultado, "Si no se alcanza el espacio, debe ser -1");
    }

    @Test
    @DisplayName("Debe encontrar la solución mínima (1) si existe")
    void testCasoSolucionUnica() {
        // 1. Arrange
        int espacioRequerido = 50;
        List<Aplicacion> apps = Stream.of(10, 10, 60, 10, 50)
                .map(this::mockApp)
                .collect(Collectors.toList());

        when(mockMemoria.getAplicacionesInstaladas()).thenReturn(apps);

        // 2. Act
        int resultado = gestor.calcularMinimoAppsADesinstalar(espacioRequerido);

        // 3. Assert
        assertEquals(1, resultado, "Debe encontrar la app de 60MB o 50MB (longitud 1)");
    }
}
