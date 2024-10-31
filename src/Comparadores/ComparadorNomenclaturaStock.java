package Comparadores;

import java.util.Comparator;

import Moneda.Stock;

public class ComparadorNomenclaturaStock implements Comparator<Stock> {

    @Override
    public int compare(Stock o1, Stock o2) {
        return o1.getNomenclatura().compareTo(o2.getNomenclatura());
    }

}
