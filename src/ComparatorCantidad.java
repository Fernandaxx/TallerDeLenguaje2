import java.util.Comparator;

public class ComparatorCantidad implements Comparator<Activo> {

    @Override
    public int compare(Activo o1, Activo o2) {
        return Double.compare(o2.getCantidad(), o1.getCantidad()); // Orden descendente
    }

}
