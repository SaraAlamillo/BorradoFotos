package borradofotos;

import java.sql.*;
import java.util.*;

/**
 * Se conecta a la base de datos para obtener las fotos utilizadas actualmente
 * en la plataforma
 *
 * @author Sara Alamillo Arroyo
 */
public class DataBase {

    private final String server;
    private final String DB;
    private final String user;
    private final String password;
    private Connection connect;

    public DataBase() throws SQLException {
        if (BorradoFotos.local) {
            this.DB = "wikkiwi_m";
            this.password = "";
            this.user = "root";
            this.server = "192.168.0.166";
        } else {
            this.DB = "wikkiwi_m";
            this.password = "\\SgO3^Va1v~5";
            this.user = "mg_wikki";
            this.server = "vps21021.inmotionhosting.com";
        }

        this.connectDB();
    }

    private void connectDB() throws SQLException {
        DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
        this.connect = DriverManager.getConnection("jdbc:mysql://" + this.server + "/" + this.DB, this.user, this.password);
    }

    public List<String> getPhotos() {
        List<String> photos = new ArrayList<>();

        try {
            
            Statement s = this.connect.createStatement();
            try (ResultSet rs = s.executeQuery("SELECT `value` FROM `wkcatalog_product_entity_media_gallery`")) {
                while (rs.next()) {
                    photos.add(BorradoFotos.pathServerPhotos + "/" + rs.getString("value"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photos;
    }
}