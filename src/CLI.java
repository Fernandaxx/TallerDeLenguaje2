public class CLI {
    public static void main(String[] args) {
        DataBaseManager gestoBD = new DataBaseManager();
        gestoBD.CreateDatabase();
        BilleteraVirtualManager gestor = new BilleteraVirtualManager();
        gestor.iniciar();
    }
}
