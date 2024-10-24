
public abstract class Activo {
    private double cantidad;

    public Activo(double cantidad) {
    }

    public Activo() {
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public abstract String obtenerNomenclatura();

}
