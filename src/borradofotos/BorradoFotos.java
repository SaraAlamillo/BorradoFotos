package borradofotos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import org.apache.commons.net.ftp.*;

/**
 * Con esta pequeña aplicación podemos eliminar todas las fotos antiguas que ya
 * no sean utilizadas por la plataforma.
 *
 * @author Sara Alamillo Arroyo
 */
public class BorradoFotos {

    public static final boolean local = true;
    public static final String pathServerPhotos = "/media/catalog/product";

    private static void listarDirectorio(FTPClient remoto, String dirPadre, String dirActual, int nivel) throws IOException {

        String dirToList = dirPadre;

        if (!dirActual.equals("")) {
            dirToList += "/" + dirActual;
        }

        FTPFile[] subFicheros = remoto.listFiles(dirToList);

        if (subFicheros != null && subFicheros.length > 0) {

            for (FTPFile subFichero : subFicheros) {

                String nombreSubFichero = subFichero.getName();
                String rutaNombreSubFichero = dirToList + "/" + nombreSubFichero;

                if (nombreSubFichero.equals(".") || nombreSubFichero.equals("..")) {
                    continue;
                }

                if (dirExcluidos.contains(nombreSubFichero)) {
                    continue;
                }

                /*for (int i = 0; i < nivel; i++) { System.out.print("\t"); }*/
                if (subFichero.isDirectory()) {
                    /*System.out.println("[" + nombreSubFichero + "]");*/
                    listarDirectorio(remoto, dirToList, nombreSubFichero, nivel + 1);
                } else {
                    if (!noBorrar.contains(rutaNombreSubFichero)) {
                        if (fotosBorradas == 0) {
                            System.out.println("FOTOS BORRADAS:");
                        }
                        System.out.println("\t" + rutaNombreSubFichero);
                        //remoto.deleteFile(rutaNombreSubFichero);
                        fotosBorradas++;
                    }
                }

            }

        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, IOException {
        
        DataBase connectDB = new DataBase();
        FTP connectFTP = new FTP();

        
    }

}
