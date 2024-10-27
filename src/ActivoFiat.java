public class ActivoFiat extends Activo {
    private Fiat fiat;

    public Fiat getFiat() {
        return fiat;
    }

    public ActivoFiat(double cantidad, Fiat fiat) {
        super(cantidad);
        this.fiat = fiat;
    }

    public ActivoFiat(double cantidad, String nomenclatura) {
        super(cantidad);
        this.fiat.setNomenclatura(nomenclatura);
    }

    public ActivoFiat() {

    }

    public void setFiat(Fiat fiat) {
        this.fiat = fiat;
    }

    @Override
    public String obtenerNomenclatura() {
        return this.fiat.getNomenclatura();
    }

}
