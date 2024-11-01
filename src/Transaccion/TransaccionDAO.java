package Transaccion;

import java.sql.*;
import java.time.LocalDateTime;

public class TransaccionDAO implements ITransaccionDAO {

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

    @Override
    public void borrarTransaccion(LocalDateTime fecha) {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            System.out.println("Opened database successfully");

            String sql = "DELETE FROM TRANSACCION WHERE FECHA_HORA = ? ";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setTimestamp(1, Timestamp.valueOf(fecha));
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                // No se encontró el activo con esa nomenclatura
                throw new RuntimeException("No se encontró una transaccion en : " + fecha);
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
