package Activo;

import Moneda.Criptomoneda;

/**
 * Representa un activo financiero específico de tipo criptomoneda.
 * Esta clase extiende la clase {@link Activo} y proporciona
 * funcionalidad para manejar activos que son criptomonedas.
 */
public class ActivoCripto extends Activo {
    private Criptomoneda cripto = new Criptomoneda();

    /**
     * Crea un nuevo activo de tipo criptomoneda por defecto.
     */
    public ActivoCripto() {
        super();
    }

    /**
     * Crea un nuevo activo cripto con la cantidad especificada
     * y una nomenclatura de criptomoneda.
     *
     * @param cantidad     La cantidad del activo cripto.
     * @param Nomenclatura La nomenclatura de la criptomoneda.
     */
    public ActivoCripto(double cantidad, String Nomenclatura) {
        super.setCantidad(cantidad);
        this.cripto.setNomenclatura(Nomenclatura);
    }

    /**
     * Crea un nuevo activo cripto con la cantidad especificada
     * y una instancia de criptomoneda.
     *
     * @param cantidad La cantidad del activo criptográfico.
     * @param cripto   La criptomoneda asociada a este activo.
     */
    public ActivoCripto(double cantidad, Criptomoneda cripto) {
        super(cantidad);
        this.cripto = cripto;
    }

    /**
     * Obtiene la criptomoneda asociada a este activo cripto.
     *
     * @return La criptomoneda asociada.
     */
    public Criptomoneda getCripto() {
        return cripto;
    }

    /**
     * Establece la criptomoneda asociada a este activo cripto.
     *
     * @param cripto La nueva criptomoneda asociada.
     */
    public void setCripto(Criptomoneda cripto) {
        this.cripto = cripto;
    }

    /**
     *
     * @return Una cadena que representa el activo cripto.
     */
    @Override
    public String toString() {
        return "ActivoCripto " + cripto.getNomenclatura() + " : " + super.getCantidad();
    }
}
