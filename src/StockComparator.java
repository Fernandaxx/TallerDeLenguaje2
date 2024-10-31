import java.util.Comparator;

import Moneda.Moneda;

public class StockComparator implements Comparator<Moneda> {
    @Override
    public int compare(Moneda m1, Moneda m2) {
        return 1;
        // return Double.compare(m2.getStock(), m1.getStock()); // Orden descendente
    }
}
