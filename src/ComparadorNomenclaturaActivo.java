import java.util.Comparator;

public class ComparadorNomenclaturaActivo implements Comparator<Activo> {

    @Override
    public int compare(Activo o1, Activo o2) {
        return o1.obtenerNomenclatura().compareTo(o2.obtenerNomenclatura());
    }

}
