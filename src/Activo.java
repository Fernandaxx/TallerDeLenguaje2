
/**
 * Clase que representa un activo dentro de la billetera virtual. Un activo
 * tiene una dirección pública, una criptomoneda asociada, un valor equivalente
 * en
 * una moneda
 * fiduciaria, y puede
 * estar asociado a diversas operaciones DeFi (Finanzas Descentralizadas).
 * 
 * @author Grupo13
 * @version 1.0
 * @since 2024
 */
public class Activo {
    private String direccionPublica;
    private double equivalenteFiduciaria;
    // private List<DeFi> DeFis = new LinkedList<>();
    private Criptomoneda criptomoneda;

    /**
     * Constructor por defecto de la clase Activo.
     */
    public Activo() {

    }

    /**
     * Calcula el valor equivalente de este activo en una moneda fiduciaria
     * específica.
     * 
     * @param tipoFiduciaria La moneda fiduciaria en la que se quiere calcular el
     *                       equivalente.
     * @return El valor equivalente en la moneda fiduciaria especificada.
     */
    public double calcularEquivalente(/* Fiduciaria */ String tipoFiduciaria) {
        return 0.0;
    }

    /**
     * Obtiene la dirección pública asociada al activo.
     * 
     * @return La dirección pública del activo.
     */
    public String getDireccionPublica() {
        return direccionPublica;
    }

    /**
     * Establece la dirección pública del activo.
     * 
     * @param direccionPublica La nueva dirección pública del activo.
     */
    public void setDireccionPublica(String direccionPublica) {
        this.direccionPublica = direccionPublica;
    }

    /**
     * Obtiene el valor equivalente del activo en términos de moneda fiduciaria.
     * 
     * @return El valor equivalente del activo en la moneda fiduciaria.
     */
    public double getEquivalenteFiduciaria() {
        return equivalenteFiduciaria;
    }

    /**
     * Establece el valor equivalente del activo en términos de moneda fiduciaria.
     * 
     * @param equivalenteFiduciaria El nuevo valor equivalente en moneda fiduciaria.
     */
    public void setEquivalenteFiduciaria(double equivalenteFiduciaria) {
        this.equivalenteFiduciaria = equivalenteFiduciaria;
    }

    /**
     * Obtiene la lista de operaciones DeFi (Finanzas Descentralizadas) asociadas a
     * este activo.
     * 
     * @return La lista de operaciones DeFi asociadas.
     */
    // public List<DeFi> getDeFis() {
    // return DeFis;
    // }

    /**
     * Establece la lista de operaciones DeFi (Finanzas Descentralizadas) para este
     * activo.
     * 
     * @param deFis La nueva lista de operaciones DeFi.
     */
    // public void setDeFis(List<DeFi> deFis) {
    // if (deFis != null)
    // DeFis = deFis;
    // }

    /**
     * Agrega una nueva operación DeFi (Finanzas Descentralizadas) a la lista de
     * DeFis asociadas con este activo.
     * 
     * @param defi La operación DeFi que se va a agregar.
     */
    // public void agregarDeFi(DeFi defi) {
    // this.DeFis.add(defi);
    // }

    /**
     * Obtiene la criptomoneda asociada a este activo.
     * 
     * @return La criptomoneda asociada.
     */
    public Criptomoneda getCriptomoneda() {
        return criptomoneda;
    }

    /**
     * Establece la criptomoneda asociada a este activo.
     * 
     * @param criptomoneda La nueva criptomoneda a asociar.
     */
    public void setCriptomoneda(Criptomoneda criptomoneda) {
        this.criptomoneda = criptomoneda;
    }

}
