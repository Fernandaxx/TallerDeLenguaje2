package Transaccion;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * La clase TransaccionDAO proporciona métodos para registrar y borrar
 * transacciones
 * en la base de datos de la billetera virtual. Implementa la interfaz
 * ITransaccionDAO.
 * 
 * @author Grupo13
 * @version 1.0
 * @since 2024
 */
public class TransaccionDAO implements ITransaccionDAO {

    /**
     * Registra una nueva transacción en la base de datos.
     *
     * @param c           La conexión a la base de datos.
     * @param transaccion La transacción a registrar.
     */
    public void registrarTransaccion(Connection c, Transaccion transaccion) {
        try {
            String sql = "INSERT INTO TRANSACCION (RESUMEN, FECHA_HORA) VALUES (?, ?)";

            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setString(1, transaccion.getResumen());
                pstmt.setTimestamp(2, Timestamp.valueOf(transaccion.getFecha()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * Elimina una transacción de la base de datos utilizando su fecha y hora.
     *
     * @param fecha La fecha y hora de la transacción a borrar.
     * @throws RuntimeException si no se encuentra una transacción con la fecha
     *                          especificada.
     */
    @Override
    public void borrarTransaccion(LocalDateTime fecha) {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            System.out.println("Opened database successfully");

            String sql = "DELETE FROM TRANSACCION WHERE FECHA_HORA = ?";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setTimestamp(1, Timestamp.valueOf(fecha));
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {

                throw new RuntimeException("No se encontró una transacción en: " + fecha);
            }
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        System.out.println("Operation done successfully");
    }
}
