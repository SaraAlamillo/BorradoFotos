package borradofotos;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import org.apache.commons.net.ftp.*;

/**
 * Con esta pequeña aplicación podemos eliminar todas las fotos antiguas que ya
 * no sean utilizadas por la plataforma.
 *
 * @author Sara Alamillo Arroyo
 */
public class BorradoFotos {
    
    private static List<String> noBorrar;
    private static final String rutaFotos = "/media/catalog/product";
    private static List<String> fotosServidor;

    /**
     * Devuelve una lista con todas las rutas de las fotos de las propiedades
     * actualmente en la plataforma
     *
     * @return List<String>
     */
    private static List<String> fotosEnWikkiWi() {
        List<String> fotos = new ArrayList<>();

        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());

            ResultSet rs;
            try (
                    Connection conexion = DriverManager.getConnection("jdbc:mysql://vps21021.inmotionhosting.com/wikkiwi_m", "mg_wikki", "\\SgO3^Va1v~5")) {
                Statement s = conexion.createStatement();
                rs = s.executeQuery("SELECT `value` FROM `wkcatalog_product_entity_media_gallery`");
                while (rs.next()) {
                    fotos.add(rutaFotos + "/" + rs.getString("value"));
                }
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fotos;
    }

    private static void listarDirectorio(FTPClient remoto, String dirPadre, String dirActual, int nivel) throws IOException {
        
        String dirToList = dirPadre;
        
        if (!dirActual.equals("")) {
            dirToList += "/" + dirActual;
        }
        
        FTPFile[] subFicheros = remoto.listFiles(dirToList);
        
        if (subFicheros != null && subFicheros.length > 0) {
            
            for (FTPFile subFichero : subFicheros) {
                
                String nombreSubFichero = subFichero.getName();
                
                if (nombreSubFichero.equals(".") || nombreSubFichero.equals("..")) { continue; }
                
                /*for (int i = 0; i < nivel; i++) { System.out.print("\t"); }*/
                
                if (subFichero.isDirectory()) {
                    /*System.out.println("[" + nombreSubFichero + "]");*/
                    listarDirectorio(remoto, dirToList, nombreSubFichero, nivel + 1);
                } else {
                    nombreSubFichero = dirPadre + "/" + nombreSubFichero;
                    fotosServidor.add(nombreSubFichero);
                }
                
            }
            
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        noBorrar = fotosEnWikkiWi();
        
        /* Servidor remoto
        String servidor = "23.235.204.50";
        int puerto = 21;
        String usuario = "salamillo@wikkiwi.com";
        String clave = "5{*:ntbS=W+x";
         *//* 
        Servidor de prueba 
         */
        String servidor = "192.168.0.166";
        int puerto = 21;
        String usuario = "admin";
        String clave = "wikkiwi";

        FTPClient remoto = new FTPClient();

        try {
            remoto.connect(servidor, puerto);

            if (!FTPReply.isPositiveCompletion(remoto.getReplyCode())) {
                System.out.println("Fallo en la conexión");
                return;
            }

            boolean success = remoto.login(usuario, clave);

            if (!success) {
                System.out.println("Fallo en la autenticación");
                return;
            }

            fotosServidor = new ArrayList<>();
            listarDirectorio(remoto, rutaFotos, "", 0);
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        } finally {
            try {
                if (remoto.isConnected()) {
                    remoto.logout();
                    remoto.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
