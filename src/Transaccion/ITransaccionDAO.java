package Transaccion;

import java.sql.Connection;
import java.time.LocalDateTime;

/**
 * La interfaz ITransaccionDAO define los métodos necesarios para gestionar
 * transacciones en la base de datos. Proporciona operaciones para registrar y
 * eliminar transacciones.
 * 
 * @author Grupo13
 * @version 1.0
 * @since 2024
 */
public interface ITransaccionDAO {

    /**
     * Registra una nueva transacción en la base de datos.
     *
     * @param c           La conexión a la base de datos.
     * @param transaccion La transacción que se desea registrar.
     */
    void registrarTransaccion(Connection c, Transaccion transaccion);

    /**
     * Elimina una transacción de la base de datos en función de su fecha.
     *
     * @param fecha La fecha de la transacción que se desea eliminar.
     */
    void borrarTransaccion(LocalDateTime fecha);
}
