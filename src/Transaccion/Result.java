package Transaccion;

public class Result {
    private boolean exito;
    private String mensaje;

    public Result() {

    }

    public Result(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
    }

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String informar() {
        String a;
        if (this.exito) {
            a = "Swap realizado de manera exitosa";
        } else {
            a = this.mensaje;
        }
        return a;
    }
}
