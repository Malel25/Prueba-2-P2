/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herencia;

/**
 *
 * @author malel
 */
public class CuentaCheques extends CuentaBancaria{
    private double limiteSobregiro;

    public CuentaCheques(int numero, String cliente, String moneda, double limiteSobregiro) {
        super(numero, cliente, moneda, TipoCuenta.CHEQUES);
        if (limiteSobregiro < 0) {
            throw new IllegalArgumentException("El limite de sobregiro no puede ser negativo.");
        }
        this.limiteSobregiro = limiteSobregiro;
    }

    public void girarCheque(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del cheque debe ser mayor a cero.");
        }
        if (monto > saldo + limiteSobregiro) {
            throw new IllegalStateException("Fondos insuficientes para girar el cheque");
        }
        saldo -= monto;
        registrarMovimiento("GIRAR CHEQUE -" + monto + " -> saldo: " + this.saldo);
    }

    @Override
    public void registrarIntereses() {
        // Tasa 0.0, no aplica
    }

    @Override
    public void quienSoy() {
        System.out.println("Soy una Cuenta de Cheques");
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Sobregiro Disp: %.2f", limiteSobregiro);
    }
}
