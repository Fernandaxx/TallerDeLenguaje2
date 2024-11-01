package Comparadores;

import java.util.Comparator;

import Moneda.Moneda;

public class ComparatorValorDolar implements Comparator<Moneda> {
    @Override
    public int compare(Moneda o1, Moneda o2) {
        return Double.compare(o2.getValor_dolar(), o1.getValor_dolar()); // Orden descendente
    }
}
