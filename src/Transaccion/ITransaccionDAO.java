package Transaccion;

import java.time.LocalDateTime;

public interface ITransaccionDAO {

    void registrarTransaccion(Transaccion transaccion, String resumen);

    void borrarTransaccion(LocalDateTime fecha);
}
