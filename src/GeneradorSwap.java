import java.sql.*;

public class GeneradorSwap implements TransaccionDAO {

    @Override
    public void simularSwap() {
        Connection c =null;  
        Statement stm =null;

        try{
            c= DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");




            c.close();

        }
    }
}
