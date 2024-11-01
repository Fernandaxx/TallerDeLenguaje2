package Comparadores;

import java.util.Comparator;

import Moneda.Stock;

public class ComparadorCantidadStock implements Comparator<Stock> {
    @Override
    public int compare(Stock o1, Stock o2) {
        return Double.compare(o2.getCantidad(), o1.getCantidad()); // Orden descendente
    }
}
