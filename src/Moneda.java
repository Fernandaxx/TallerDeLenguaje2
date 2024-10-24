public class Moneda {
    private char tipo;
    private String nombre;
    private String nomenclatura;
    private double valor_dolar;
    private double volatilidad;
    private double stock;

    public char getTipo() {
        return tipo;
    }

    public Moneda(char tipo, String nombre, String nomenclatura, double valor_dolar, double volatilidad, double stock) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.nomenclatura = nomenclatura;
        this.valor_dolar = valor_dolar;
        this.volatilidad = volatilidad;
        this.stock = stock;
    }

    public Moneda() {
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public double getValor_dolar() {
        return valor_dolar;
    }

    public void setValor_dolar(double valor_dolar) {
        this.valor_dolar = valor_dolar;
    }

    public double getVolatilidad() {
        return volatilidad;
    }

    public void setVolatilidad(double volatilidad) {
        this.volatilidad = volatilidad;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    @Override
public String toString() {
    return "Moneda :\n" +
           "\tTipo = " + tipo + "\n" +
           "\tNombre = '" + nombre + "',\n" +
           "\tNomenclatura = '" + nomenclatura + "'\n" +
           "\tValor en Dólares = " + valor_dolar + "\n" +
           "\tVolatilidad = " + volatilidad + "\n" +
           "\tStock = " + stock + "\n" ;
}
    

}
