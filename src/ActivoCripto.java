public class ActivoCripto extends Activo {
    private String direccion;
    private Criptomoneda cripto = new Criptomoneda();

    public String getDireccion() {
        return direccion;
    }

    public ActivoCripto() {
        super();

    }

    public ActivoCripto(double cantidad, String Nomenclatura) {
        super.setCantidad(cantidad);
        this.cripto.setNomenclatura(Nomenclatura);

    }

    public ActivoCripto(double cantidad, String direccion, Criptomoneda cripto) {
        super(cantidad);
        this.direccion = direccion;
        this.cripto = cripto;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Criptomoneda getCripto() {
        return cripto;
    }

    public void setCripto(Criptomoneda cripto) {
        this.cripto = cripto;
    }

    @Override
    public String obtenerNomenclatura() {
        return this.cripto.getNomenclatura();
    }

}
