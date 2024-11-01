package Activo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz que define los métodos para gestionar la persistencia de activos
 * cripto en una base de datos.
 */
public interface IActivoCriptoDAO {

    /**
     * Genera un nuevo activo cripto en la base de datos.
     *
     * @param activoCripto El activo cripto a generar.
     * @return true si la operación fue exitosa; false en caso contrario.
     */
    boolean generarActivoCripto(ActivoCripto activoCripto);

    /**
     * Lista todos los activos cripto en la base de datos.
     *
     * @return Una lista de activos cripto.
     */
    List<ActivoCripto> listarActivosCripto();

    /**
     * Elimina un activo cripto de la base de datos por su nomenclatura o
     * identificador.
     *
     * @param dato La nomenclatura o identificador del activo cripto a
     *             eliminar.
     */
    void borrarActivoCripto(String dato);

    /**
     * Verifica si un activo cripto ya existe en la base de datos.
     *
     * @param c            La conexión a la base de datos.
     * @param nomenclatura La nomenclatura del activo cripto a verificar.
     * @return true si el activo existe; false en caso contrario.
     * @throws Exception Si ocurre un error durante la verificación.
     */
    boolean activoExiste(Connection c, String nomenclatura) throws Exception;

    /**
     * Actualiza un activo cripto existente en la base de datos.
     *
     * @param c      La conexión a la base de datos.
     * @param activo El activo cripto a actualizar.
     * @throws SQLException Si ocurre un error en la consulta a la base de datos.
     */
    void actualizarActivo(Connection c, ActivoCripto activo) throws SQLException;

    /**
     * Inserta un nuevo activo cripto en la base de datos.
     *
     * @param c      La conexión a la base de datos.
     * @param activo El activo cripto a insertar.
     * @throws SQLException Si ocurre un error en la consulta a la base de datos.
     */
    void insertarActivo(Connection c, ActivoCripto activo) throws SQLException;

    /**
     * Verifica si hay suficiente cantidad de un activo cripto en la base de
     * datos.
     *
     * @param c            La conexión a la base de datos.
     * @param nomenclatura La nomenclatura del activo cripto.
     * @param cantidad     La cantidad a verificar.
     * @return true si hay suficiente cantidad; false en caso contrario.
     * @throws SQLException Si ocurre un error en la consulta a la base de datos.
     */
    boolean verificarCantidad(Connection c, String nomenclatura, double cantidad) throws SQLException;

}
