package Comparadores;

import java.util.Comparator;

import Moneda.Moneda;

public class ComparatorNomenclaturaMoneda implements Comparator<Moneda> {

    @Override
    public int compare(Moneda o1, Moneda o2) {
        return o1.getNomenclatura().compareTo(o2.getNomenclatura());
    }

}