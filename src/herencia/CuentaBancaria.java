
package herencia;
import java.util.Calendar;
import java.util.ArrayList;

public class CuentaBancaria {
    protected int numero;
    protected String cliente;
    protected double saldo;
    protected double tasa;
    protected Calendar creacion;
    protected ArrayList<String> historial;
    
    public CuentaBancaria(int numero, String cliente, double tasa){
        
        this.numero = numero;
        this.cliente = cliente;
        this.tasa = tasa;
        this.saldo = 500;
        this.creacion = Calendar.getInstance();
        this.historial = new ArrayList<>();
        this.historial.add("Cuenta creada con saldo inicial de 500Lps.");
        
    }
    
    public void depositar(double monto){
        try{
            if(monto <= 0){
                System.out.println("Error: Insuficiente Saldo");
            }else{
                this.saldo += monto;
                historial.add("Deposito : " + monto);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public boolean retirar(double monto){
        if(monto > 0 && saldo <= monto){
            saldo -= monto;
            historial.add("Retiro : " + monto);
            return true;
        }
        historial.add("Intento de retiro fallido : "+monto);
        return false;
    }
    
    public void mostrarHistorial(){
        System.out.println("==== Historial De "+ cliente.toUpperCase()+"====");
        for(String registro : historial){
            System.out.println(registro);
        }
    }
    
    public double getSaldo(){
        return saldo;
    }
    
    public String toString(){
        return String.format("Cuenta #%d | %s | %.2f", numero, cliente, saldo);
    }
    
    public void transferir(CuentaBancaria destino, double monto){
        if(this.retirar(monto)){
            destino.depositar(monto);
            this.historial.add("Transferencia enviada a #"+destino.numero+" : "+monto);
        }else{
            System.out.println("Transferencia fallida...");
        }
    }
}