
package herencia;
import java.util.Calendar;
import java.util.ArrayList;

public class CuentaBancaria {
    protected int numero;
    protected String cliente;
    protected double saldo;
    protected double tasa;
    protected Calendar creacion;
    protected String [] historial;
    protected String moneda;
    protected TipoCuenta tipo;
    protected int cantMovimientos;
    
    
    public CuentaBancaria(int numero, String cliente, double tasa){
        
        this.numero = numero;
        this.cliente = cliente;
        this.tasa = tasa;
        this.saldo = 500;
        this.creacion = Calendar.getInstance();
        this.moneda = "Lps";
        this.tipo = TipoCuenta.CHEQUES;
        registrarMovimiento("Cuenta creada con saldo inicial de 500Lps.");
    }
    
    public CuentaBancaria(int numero, String cliente, String moneda, TipoCuenta tipo){
        this.numero = numero;
        this.cliente = cliente;
        this.tasa = tipo.tasa();
        this.saldo = 500;
        this.creacion = Calendar.getInstance();
        this.moneda = moneda;
        this.tipo = tipo;
        registrarMovimiento("Cuenta creada (" + tipo + ") con saldo inicial de 500 " + moneda);
    }
    
    public void registrarMovimiento(String linea) {
        try {
            if (cantMovimientos >= historial.length) {
                throw new IllegalStateException("El historial de movimientos esta lleno.");
            }
            historial[cantMovimientos] = linea;
            cantMovimientos++;
        } catch (IllegalStateException e) {
            System.out.println("Aviso: " + e.getMessage());
        }
    }
    
    public void depositar(double monto){
        try{
            if(monto <= 0){
                System.out.println("Error: Insuficiente Saldo");
            }else{
                this.saldo += monto;
                registrarMovimiento("DEPOSITO +" + monto + " -> saldo: " + this.saldo);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public boolean retirar(double monto){
        if(monto > 0 && saldo <= monto){
            saldo -= monto;
            registrarMovimiento("Retiro : " + monto);
            return true;
        }
        registrarMovimiento("Intento de retiro fallido : " + monto);
        return false;
    }
    
    public void mostrarHistorial(){
        System.out.println("==== Historial De "+ cliente.toUpperCase()+"====");
        for(String registro : historial){
            System.out.println(registro);
        }
    }
    
    public void reintegrar(double monto) {
        this.saldo += monto;
        registrarMovimiento("Reversa/Reintegro +" + monto + " con saldo: " + this.saldo);
    }
    
    public void registrarIntereses() {
        double intereses = this.saldo * this.tasa;
        if (intereses > 0) {
            this.saldo += intereses;
            registrarMovimiento("Intereses +" + intereses + "con saldo: " + this.saldo);
        }
    }
    
    public void quienSoy() {
        System.out.println("Soy una Cuenta Bancaria");
    }
    
    public double getSaldo(){
        return saldo;
    }
    
    public int getNumero(){
        return numero;
    }
    
    public String toString(){
        return String.format("Cuenta #%d | %s | Saldo: %.2f %s | Tipo: %s | Apertura %tF", numero, cliente, saldo, moneda, (tipo != null ? tipo : "CHEQUES"), creacion.getTime());
    }
    
    public void transferir(CuentaBancaria destino, double monto){
        if(this.retirar(monto)){
            try{
            destino.depositar(monto);
            this.registrarMovimiento("Transferencia enviada a #"+destino.numero+" : "+monto);
            }catch(Exception e){
                this.reintegrar(monto);
                System.out.println("ERROR: "+e.getMessage());
            }
        }else{
            System.out.println("Transferencia fallida...");
        }
    }
}