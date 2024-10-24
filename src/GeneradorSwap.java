import java.sql.*;

public class GeneradorSwap implements TransaccionDAO {

    @Override
    public void simularSwap() {
        c =null;
        stm =null;

        try{
            c= DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
        }
    }
}
