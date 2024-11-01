package Activo;

import Moneda.Fiat;

public class ActivoFiat extends Activo {
    private Fiat fiat = new Fiat();

    public Fiat getFiat() {
        return fiat;
    }

    public ActivoFiat(double cantidad, Fiat fiat) {
        super(cantidad);
        this.fiat = fiat;
    }

    public ActivoFiat(double cantidad, String nomenclatura) {
        super.setCantidad(cantidad);
        this.fiat.setNomenclatura(nomenclatura);
    }

    public ActivoFiat() {

    }

    public void setFiat(Fiat fiat) {
        this.fiat = fiat;
    }

    @Override
    public String toString() {
        return "ActivoFiat " + fiat.getNomenclatura() + " : " + super.getCantidad();
    }

}
