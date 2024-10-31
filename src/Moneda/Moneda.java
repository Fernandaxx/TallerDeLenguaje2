package Moneda;
///////////////------FALTAN COMENTARIOS-------/////////////////

//////////////////////////////////////////////////////////////

/**
 * La clase abstracta Moneda modela una moneda en el sistema Billetera
 * Virtual, con su
 * volatilidad y el stock de la misma.
 * 
 * @author Grupo13
 * @version 2.0
 * @since 2024
 */
public abstract class Moneda {
    private char tipo;
    private String nombre;
    private String nomenclatura;
    private double valor_dolar;

    /**
     * Constructor de la clase Moneda.
     */

    public Moneda(char tipo, String nombre, String nomenclatura, double valor_dolar) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.nomenclatura = nomenclatura;
        this.valor_dolar = valor_dolar;

    }

    /**
     * Constructor por defecto de la clase Moneda.
     */

    public Moneda() {
    }

    public Moneda(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    /**
     * Obtiene el tipo de la moneda.
     * 
     * @return El tipo de la moneda.
     */
    public char getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de la moneda.
     * 
     * @param nombre El tipo de la moneda.
     */

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

    @Override
    public String toString() {
        return "Moneda :\n" +
                "\tTipo = " + tipo + "\n" +
                "\tNombre = '" + nombre + "',\n" +
                "\tNomenclatura = '" + nomenclatura + "'\n" +
                "\tValor en Dólares = " + valor_dolar + "\n";
    }

}
