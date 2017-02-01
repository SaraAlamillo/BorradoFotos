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
    private static List<String> noBorrar;
    // private static final String rutaFotos = "/public_html/media/catalog/product";
    private static List<String> dirExcluidos;
    private static int fotosBorradas;


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
    public static void main(String[] args) throws SQLException {

        dirExcluidos = new ArrayList<>();
        dirExcluidos.add("cache");
        dirExcluidos.add("thumbs");
        dirExcluidos.add("watermark");

        fotosBorradas = 0;
        
        DataBase connectDB = new DataBase();

        noBorrar = connectDB.getPhotos();

        FTPClient remoto = new FTPClient();

        try {
            
            listarDirectorio(remoto, pathServerPhotos, "", 0);

            System.out.println("SE HAN BORRADO " + fotosBorradas + " FOTOS.");

        } catch (IOException ex) {
            System.out.println("¡Oops! Esto no deberia estar pasando...");
            ex.printStackTrace();
        }
    }

}
