package Comparadores;

import java.util.Comparator;

import Activo.ActivoCripto;

public class ComparadorNomenclaturaActivoCripto implements Comparator<ActivoCripto> {

    @Override
    public int compare(ActivoCripto o1, ActivoCripto o2) {
        return o1.getCripto().getNomenclatura().compareTo(o2.getCripto().getNomenclatura());
    }

}
