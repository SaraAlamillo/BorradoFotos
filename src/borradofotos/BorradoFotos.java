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

    public static final boolean local = true;

    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, IOException {
        
        DataBase connectDB = new DataBase();
        FTP connectFTP = new FTP();

        
    }

}
