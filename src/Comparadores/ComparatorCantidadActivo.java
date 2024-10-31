package Comparadores;

import java.util.Comparator;

import Activo.Activo;

public class ComparatorCantidadActivo implements Comparator<Activo> {

    @Override
    public int compare(Activo o1, Activo o2) {
        return Double.compare(o2.getCantidad(), o1.getCantidad()); // Orden descendente
    }

}
