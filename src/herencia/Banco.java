/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herencia;

/**
 *
 * @author malel
 */
public class Banco {
    private CuentaBancaria[] cuentas = new CuentaBancaria[20];
    private int cantidadCuentas = 0;

    public void agregarCuenta(CuentaBancaria c) {
        if (cantidadCuentas >= cuentas.length) {
            throw new IllegalStateException("El banco esta lleno, no se pueden agregar mas cuentas.");
        }
        try {
            buscarPorNumero(c.getNumero());
            throw new IllegalArgumentException("El numero de cuenta ya existe.");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("El numero de cuenta ya existe.")) {
                throw e;
            }
            cuentas[cantidadCuentas] = c;
            cantidadCuentas++;
        }
    }

    public CuentaBancaria buscarPorNumero(int numero) {
        return buscarPorNumeroRecursivo(numero, 0);
    }

    private CuentaBancaria buscarPorNumeroRecursivo(int numero, int indice) {
        if (indice >= cantidadCuentas) {
            throw new IllegalArgumentException("Cuenta no encontrada con numero: " + numero);
        }
        if (cuentas[indice].getNumero() == numero) {
            return cuentas[indice];
        }
        return buscarPorNumeroRecursivo(numero, indice + 1);
    }

    public double saldoTotalDelBanco() {
        return sumarSaldosRecursivo(0);
    }

    private double sumarSaldosRecursivo(int indice) {
        if (indice >= cantidadCuentas) {
            return 0.0;
        }
        return cuentas[indice].getSaldo() + sumarSaldosRecursivo(indice + 1);
    }

    public void ordenarPorSaldo() {
        if (cantidadCuentas > 1) {
            ordenarRecursivo(cantidadCuentas - 1);
        }
    }

    private void ordenarRecursivo(int n) {
        if (n == 0) return;
        for (int i = 0; i < n; i++) {
            if (cuentas[i].getSaldo() > cuentas[i + 1].getSaldo()) {
                CuentaBancaria temp = cuentas[i];
                cuentas[i] = cuentas[i + 1];
                cuentas[i + 1] = temp;
            }
        }
        ordenarRecursivo(n - 1);
    }

    public void transferir(int numeroOrigen, int numeroDestino, double monto) {
        if (numeroOrigen == numeroDestino) {
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta.");
        }
        CuentaBancaria origen = buscarPorNumero(numeroOrigen);
        CuentaBancaria destino = buscarPorNumero(numeroDestino);

        origen.transferir(destino, monto);
    }

    public void aplicarInteresesGlobal() {
        for (int i = 0; i < cantidadCuentas; i++) {
            try {
                cuentas[i].registrarIntereses();
            } catch (Exception e) {
                System.out.println("Error al aplicar intereses en cuenta #" + cuentas[i].getNumero() + ": " + e.getMessage());
            }
        }
    }

    public void generarReporte() {
        ordenarPorSaldo();
        System.out.println("\n===== REPORTE GENERAL DEL BANCO =====");
        for (int i = 0; i < cantidadCuentas; i++) {
            cuentas[i].quienSoy();
            System.out.println(cuentas[i].toString());
            System.out.println("-----------------------------------");
        }
        System.out.printf("SALDO TOTAL DEL BANCO: %.2f\n", saldoTotalDelBanco());
    }
}
