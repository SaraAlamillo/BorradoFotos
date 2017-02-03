package borradofotos;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Con esta pequeña aplicación podemos eliminar todas las fotos antiguas que ya
 * no sean utilizadas por la plataforma.
 *
 * @author Sara Alamillo Arroyo
 */
public class BorradoFotos {

    public static boolean local = true;

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws SQLException, IOException {
        /*
        int opcionInt = 0;
        String opcionString = "";
        Scanner in;
        DataBase connectDB;
        FTP connectFTP;
        List<String> dbPhotos;
        List<String> oldPhotos;

        System.out.println("Bienvenido humano");
        System.out.println();

        System.out.println("¿Va trabajar con el servidor local o remoto?");
        System.out.println("\t1. Local");
        System.out.println("\t2. Remoto");
        while (opcionInt != 1 && opcionInt != 2) {
            System.out.print("Opción: ");
            in = new Scanner(System.in);
            if (in.hasNextInt()) {
                opcionInt = in.nextInt();
            }
        }
        local = opcionInt != 2;
        System.out.println();

        System.out.println("...Conectando al FTP...");
        connectFTP = new FTP();
        System.out.println("...Cotilleando las fotos del servidor...");
        connectFTP.setPhotosServer();
        System.out.println("...Conectando a la base de datos...");
        connectDB = new DataBase();
        System.out.println("...Cotilleando las fotos de la base de datos...");
        dbPhotos = connectDB.getPhotos();
        System.out.println("...Comparando las fotos de ambos sitios...");
        oldPhotos = connectFTP.getOldPhotos(dbPhotos);

        if (oldPhotos.isEmpty()) {
            System.out.println("No se puede eliminar ninguna foto. Todas están siendo utilizadas actualmente.");
        } else {
            System.out.println("Fotos del servidor que no aparecen en la base de datos:");
            oldPhotos.forEach((f) -> {
                System.out.println("\t-> " + f);
            });
            System.out.print("Se van a borrar " + oldPhotos.size() + " fotos, ¿desea continuar? ");
            while (!"si".equals(opcionString.toLowerCase()) && !"no".equals(opcionString.toLowerCase()) && !"s".equals(opcionString.toLowerCase()) && !"n".equals(opcionString.toLowerCase())) {
                in = new Scanner(System.in);
                if (in.hasNextLine()) {
                    opcionString = in.nextLine();
                }

            }

            if ("si".equals(opcionString.toLowerCase()) || "s".equals(opcionString.toLowerCase())) {
                int numDel = connectFTP.deletePhotos(oldPhotos);
                System.out.println("Se han borrado un total de " + numDel + " fotos.");
            }
        }

        System.out.println();
        System.out.print("¿Desea borrar la caché de las fotos? ");
        while (!"si".equals(opcionString.toLowerCase()) && !"no".equals(opcionString.toLowerCase()) && !"s".equals(opcionString.toLowerCase()) && !"n".equals(opcionString.toLowerCase())) {
            in = new Scanner(System.in);
            if (in.hasNextLine()) {
                opcionString = in.nextLine();
            }

        }

        if ("si".equals(opcionString.toLowerCase()) || "s".equals(opcionString.toLowerCase())) {
            System.out.println("...Quitando chasca de la caché...");
            if (connectFTP.deleteCache()) {
                System.out.println("Se ha borrado la memoria caché de las fotos");
            } else {
                System.out.println("Esto no debería estar pasando... La caché no se ha podido borrar...");
            }
        }

        System.out.println();
        System.out.println("Adios humano");
         */
        Image img = new Image();
        img.optimize("C:\\Users\\pc\\Pictures\\test.jpg", 0.05f);

    }
}
