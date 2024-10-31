package Activo;

import Moneda.Criptomoneda;

public class ActivoCripto extends Activo {
    private Criptomoneda cripto = new Criptomoneda();

    public ActivoCripto() {
        super();
    }

    public ActivoCripto(double cantidad, String Nomenclatura) {
        super.setCantidad(cantidad);
        this.cripto.setNomenclatura(Nomenclatura);

    }

    public ActivoCripto(double cantidad, Criptomoneda cripto) {
        super(cantidad);
        this.cripto = cripto;
    }

    public Criptomoneda getCripto() {
        return cripto;
    }

    public void setCripto(Criptomoneda cripto) {
        this.cripto = cripto;
    }

    @Override
    public String toString() {
        return "ActivoCripto " + cripto.getNomenclatura() + " : " + super.getCantidad();
    }

}
