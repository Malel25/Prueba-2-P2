/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herencia;
import java.util.Calendar;
/**
 *
 * @author malel
 */
public class CuentaPlazoFijo extends CuentaBancaria {
    private Calendar findePlazo;
    private double intereses;

    public CuentaPlazoFijo(int numero, String cliente, String moneda) {
        super(numero, cliente, moneda, TipoCuenta.PLAZO);
        this.intereses = 0.0;
    }

    public void setPlazo(int anio, int mes, int dia) {
        try {
            Calendar fecha = Calendar.getInstance();
            fecha.setLenient(false);
            fecha.set(anio, mes - 1, dia);
            fecha.getTime(); 
            this.findePlazo = fecha;
        } catch (Exception e) {
            throw new IllegalArgumentException("Fecha de plazo invalida.");
        }
    }

    public boolean estaVencida() {
        if (findePlazo == null) return false;
        Calendar hoy = Calendar.getInstance();
        return !hoy.before(findePlazo);
    }

    @Override
    public void registrarIntereses() {
        double calculo = this.saldo * this.tasa;
        if (!estaVencida()) {
            this.intereses += calculo;
            registrarMovimiento("INTERESES ACUMULADOS (Plazo Fijo) +" + calculo + " -> Intereses: " + this.intereses);
        } else {
            super.registrarIntereses();
        }
    }

    @Override
    public boolean retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a cero.");
        }
        if (!estaVencida()) {
            if (monto > this.intereses) {
                registrarMovimiento("Intento de retiro fallido (Antes de vencer solo retira intereses) : " + monto);
                return false;
            }
            this.intereses -= monto;
            registrarMovimiento("RETIRO DE INTERESES -" + monto + " -> Intereses acum.: " + this.intereses);
            return true;
        } else {
            if (this.intereses > 0) {
                this.saldo += this.intereses;
                registrarMovimiento("INTERESES TRANSFERIDOS A SALDO PRINCIPAL: " + this.intereses);
                this.intereses = 0.0;
            }
            return super.retirar(monto);
        }
    }

    @Override
    public void reintegrar(double monto) {
        if (!estaVencida()) {
            this.intereses += monto;
            registrarMovimiento("REVERSA/REINTEGRO A INTERESES +" + monto + " -> Intereses: " + this.intereses);
        } else {
            super.reintegrar(monto);
        }
    }

    @Override
    public void quienSoy() {
        System.out.println("Soy una CuentaPlazoFijo");
    }

    @Override
    public String toString() {
        String vencimientoStr = (findePlazo != null) ? String.format("%tF", findePlazo.getTime()) : "No definido";
        return super.toString() + String.format(" | Intereses Acum: %.2f | Vencimiento: %s", intereses, vencimientoStr);
    }
}
