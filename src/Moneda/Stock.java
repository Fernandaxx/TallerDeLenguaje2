package Moneda;

public class Stock {

    private String nomenclatura;
    private double cantidad;

    public Stock(String nomenclatura, double cantidad) {
        this.nomenclatura = nomenclatura;
        this.cantidad = cantidad;
    }

    public Stock() {
    }

    public String getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

}
