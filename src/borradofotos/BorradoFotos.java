package borradofotos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Con esta pequeña aplicación podemos eliminar todas las fotos antiguas que ya
 * no sean utilizadas por la plataforma.
 *
 * @author Sara Alamillo Arroyo
 */
public class BorradoFotos {

    public static boolean local;

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws SQLException, IOException {

        int opcion = 0;
        Scanner in;
        DataBase connectDB;
        FTP connectFTP;

        System.out.println("Bienvenido humano");
        System.out.println();
        
        System.out.println("¿Va trabajar con el servidor local o remoto?");
        System.out.println("\t1. Local");
        System.out.println("\t2. Remoto");
        while (opcion != 1 && opcion != 2) {
            System.out.print("Opción: ");
            in = new Scanner(System.in);
            if (in.hasNextInt()) {
                opcion = in.nextInt();
            }
        }
        local = opcion != 2;
        System.out.println();
        
        connectFTP = new FTP();
    }

}
