
package herencia;
import java.util.Calendar;
public class Herencia {

    public static void main(String[] args) {
        // Funcion calendar
        Calendar fecha = Calendar.getInstance();
        System.out.println(fecha);
        
        // Sacar anio
        int year = fecha.get(Calendar.YEAR);
        System.out.println(year);
    }
    
}
