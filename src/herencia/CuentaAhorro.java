
package herencia;
import java.util.Calendar;

public class CuentaAhorro extends CuentaBancaria {
    
    private Calendar ultimaModif;
    private int retirosRealizados;
    private static final int MAX_RETIROS_MES = 3;
    
    public CuentaAhorro(int numero, String cliente, String moneda){
        super(numero, cliente, moneda, TipoCuenta.AHORRO);
        this.ultimaModif = Calendar.getInstance();
    }
    
    public boolean status(){
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.add(Calendar.MONTH, -6);
        return ultimaModif.after(fechaActual);
    }
    
    public void aplicarIntereses(){
        if(status()){
         double interes = saldo * tasa;
            depositar(interes);
            registrarMovimiento("Intereses aplicados: +"+interes);
            this.ultimaModif = Calendar.getInstance();
        } else {
            System.out.println("Cuenta inactiva, no se pueden aplicar intereses.");
        }
    }
    
    private void verificarActividad() {
        if(!status()){
            throw new IllegalStateException("La cuenta de ahorros esta inactiva por falta de movimientos en 6 meses.");
        }
    }

    @Override
    public void depositar(double monto) {
        verificarActividad();
        super.depositar(monto);
        this.ultimaModif = Calendar.getInstance();
    }

    @Override
    public boolean retirar(double monto) {
        verificarActividad();
        boolean res = super.retirar(monto);
        if(res){
            this.ultimaModif = Calendar.getInstance();
        }
        return res;
    }  
    
    @Override
    public void registrarIntereses() {
        aplicarIntereses();
    }

    @Override
    public void quienSoy() {
        System.out.println("Soy una CuentaAhorro");
    }
}
