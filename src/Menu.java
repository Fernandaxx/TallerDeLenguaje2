public class Menu {
    public static void main(String[] args) {
        ActivoDao act = new ActivoDaoImpl();
        act.GenerarActivo("DOGE", 8);
        act.listarActivos();

    }
}
