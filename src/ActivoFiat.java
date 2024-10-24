public class ActivoFiat extends Activo {
    private Fiat fiat;

    public Fiat getFiat() {
        return fiat;
    }

    public void setFiat(Fiat fiat) {
        this.fiat = fiat;
    }

    @Override
    public String obtenerNomenclatura() {
        return this.fiat.getNomenclatura();
    }

}
