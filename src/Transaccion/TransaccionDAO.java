package Transaccion;

import Moneda.Criptomoneda;

public interface TransaccionDAO {

    void simularSwap(Criptomoneda criptoInicial, double cantidad, Criptomoneda criptoFinal);

}
