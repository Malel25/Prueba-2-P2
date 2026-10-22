
package herencia;
import java.util.Calendar;
import java.util.Scanner;

public class Herencia {

    public static void main(String[] args) {
       Scanner t = new Scanner(System.in);
        Banco banco = new Banco();
        int opcion = 0;

        do {
            System.out.println("\n--- BANCO FINANZAS DEL VALLE ---");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Depositar en una cuenta");
            System.out.println("3. Retirar en una cuenta");
            System.out.println("4. Transferir entre dos cuentas");
            System.out.println("5. Aplicar intereses a todas las cuentas");
            System.out.println("6. Ver reporte ordenado por saldo y saldo total");
            System.out.println("7. Ver historial de movimientos de una cuenta");
            System.out.println("8. Girar cheque (solo cuentas Cheques)");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(t.nextLine());

                switch (opcion) {
                    case 1:
                        System.out.print("Tipo de cuenta (1. Ahorro, 2. Cheques, 3. Plazo Fijo): ");
                        int tipo = Integer.parseInt(t.nextLine());
                        System.out.print("Numero de cuenta: ");
                        
                        int num = Integer.parseInt(t.nextLine());
                        System.out.print("Nombre del cliente: ");
                        String cliente = t.nextLine();
                        System.out.print("Moneda (ej. Lps, USD): ");
                        String moneda = t.nextLine();

                        if (tipo == 1) {
                            CuentaAhorro cAhorro = new CuentaAhorro(num, cliente, moneda);
                            banco.agregarCuenta(cAhorro);
                            System.out.println("¡Cuenta de Ahorro creada con exito!");
                        } else if (tipo == 2) {
                            System.out.print("Ingrese limite de sobregiro: ");
                            double sobregiro = Double.parseDouble(t.nextLine());
                            CuentaCheques cCheques = new CuentaCheques(num, cliente, moneda, sobregiro);
                            banco.agregarCuenta(cCheques);
                            System.out.println("¡Cuenta de Cheques creada con exito!");
                        } else if (tipo == 3) {
                            CuentaPlazoFijo cPlazo = new CuentaPlazoFijo(num, cliente, moneda);
                            System.out.print("Anio de vencimiento: ");
                            int anio = Integer.parseInt(t.nextLine());
                            System.out.print("Mes de vencimiento (1-12): ");
                            int mes = Integer.parseInt(t.nextLine());
                            System.out.print("Dia de vencimiento: ");
                            int dia = Integer.parseInt(t.nextLine());
                            cPlazo.setPlazo(anio, mes, dia);
                            banco.agregarCuenta(cPlazo);
                            System.out.println("¡Cuenta de Plazo Fijo creada con exito!");
                        } else {
                            System.out.println("Tipo de cuenta invalido.");
                        }
                        break;

                    case 2:
                        System.out.print("Numero de cuenta: ");
                        int numDep = Integer.parseInt(t.nextLine());
                        System.out.print("Monto a depositar: ");
                        double montoDep = Double.parseDouble(t.nextLine());
                        CuentaBancaria cuentaDep = banco.buscarPorNumero(numDep);
                        cuentaDep.depositar(montoDep);
                        System.out.println("¡Deposito realizado con exito!");
                        break;

                    case 3:
                        System.out.print("Numero de cuenta: ");
                        int numRet = Integer.parseInt(t.nextLine());
                        System.out.print("Monto a retirar: ");
                        double montoRet = Double.parseDouble(t.nextLine());
                        CuentaBancaria cuentaRet = banco.buscarPorNumero(numRet);
                        if (cuentaRet.retirar(montoRet)) {
                            System.out.println("¡Retiro realizado con exito!");
                        } else {
                            System.out.println("No se pudo realizar el retiro (Fondos insuficientes o restricciones).");
                        }
                        break;

                    case 4:
                        System.out.print("Numero de cuenta origen: ");
                        int origen = Integer.parseInt(t.nextLine());
                        System.out.print("Numero de cuenta destino: ");
                        int destino = Integer.parseInt(t.nextLine());
                        System.out.print("Monto a transferir: ");
                        double montoTrans = Double.parseDouble(t.nextLine());
                        banco.transferir(origen, destino, montoTrans);
                        System.out.println("¡Transferencia completada con exito!");
                        break;

                    case 5:
                        banco.aplicarInteresesGlobal();
                        System.out.println("¡Intereses aplicados a todas las cuentas elegibles!");
                        break;

                    case 6:
                        banco.generarReporte();
                        break;

                    case 7:
                        System.out.print("Numero de cuenta: ");
                        int numHist = Integer.parseInt(t.nextLine());
                        CuentaBancaria cuentaHist = banco.buscarPorNumero(numHist);
                        cuentaHist.mostrarHistorial();
                        break;

                    case 8:
                        System.out.print("Numero de cuenta de cheques: ");
                        int numCheq = Integer.parseInt(t.nextLine());
                        CuentaBancaria cuentaBuscada = banco.buscarPorNumero(numCheq);
                        if (cuentaBuscada instanceof CuentaCheques) {
                            System.out.print("Monto del cheque a girar: ");
                            double montoCheq = Double.parseDouble(t.nextLine());
                            ((CuentaCheques) cuentaBuscada).girarCheque(montoCheq);
                            System.out.println("¡Cheque girado con exito!");
                        } else {
                            System.out.println("La cuenta ingresada no es de tipo Cheques.");
                        }
                        break;

                    case 9:
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 9);
    }
}
