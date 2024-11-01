package Transaccion;

import java.sql.Connection;
import java.time.LocalDateTime;

public interface ITransaccionDAO {

    void registrarTransaccion(Connection c, Transaccion transaccion);

    void borrarTransaccion(LocalDateTime fecha);
}
