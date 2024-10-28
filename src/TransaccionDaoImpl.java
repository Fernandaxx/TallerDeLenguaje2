import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransaccionDaoImpl {

    public void registrarTransaccion(Connection c, Transaccion transaccion, String resumen) throws SQLException{
        String sql ="INSERT INTO TRANSACCION (RESUMEN , FECHA_HORA) VALUES (?,?)";
        try(PreparedStatement pstmt =c.prepareStatement(sql)){
            pstmt.setString(1, resumen);
            pstmt.setDate(2,transaccion.getFecha());

             LocalDateTime dateTime = transaccion.getFecha().atStartOfDay();
            pstmt.setTimestamp(2, Timestamp.valueOf(dateTime));
            
            pstmt.executeUpdate();
        }

}
