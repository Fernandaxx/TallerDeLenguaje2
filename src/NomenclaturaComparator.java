import java.util.Comparator;

public class NomenclaturaComparator implements Comparator<Moneda> {
    @Override
    public int compare(Moneda m1, Moneda m2) {
        return m1.getNomenclatura().compareTo(m2.getNomenclatura());
    }
}
