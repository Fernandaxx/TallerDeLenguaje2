public class ActivoCripto extends Activo {
    private String direccion;
    private Criptomoneda cripto;

    public String getDireccion() {
        return direccion;
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
