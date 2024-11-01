package Comparadores;

import java.util.Comparator;

import Activo.ActivoFiat;

public class ComparadorNomenclaturaActivoFiat implements Comparator<ActivoFiat> {
    @Override
    public int compare(ActivoFiat o1, ActivoFiat o2) {
        return o1.getFiat().getNomenclatura().compareTo(o2.getFiat().getNomenclatura());
    }

}
