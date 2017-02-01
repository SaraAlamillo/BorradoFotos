package borradofotos;

import java.io.IOException;
import java.util.*;
import org.apache.commons.net.ftp.*;

/**
 * Realiza las conexiones con el servidor FTP
 *
 * @author Sara Alamillo Arroyo
 */
public class FTP {

    private final String server;
    private final int port;
    private final String user;
    private final String password;
    private FTPClient connect;
    private List<String> notTouch;

    public FTP() throws IOException {
        this.notTouch = new ArrayList<>();
        this.notTouch.add("cache");
        this.notTouch.add("thumbs");
        this.notTouch.add("watermark");
        
        this.port = 21;
        if (BorradoFotos.local) {
            this.server = "192.168.0.166";
            this.user = "admin";
            this.password = "wikkiwi";
        } else {
            this.user = "salamillo@wikkiwi.com";
            this.password = "5{*:ntbS=W+x";
            this.server = "23.235.204.50";
        }
        this.connectFTP();
    }

    private void connectFTP() throws IOException {
        
        this.connect = new FTPClient();
        this.connect.connect(this.server, this.port);

        if (!FTPReply.isPositiveCompletion(this.connect.getReplyCode())) {
            System.out.println("Fallo en la conexión al servidor FTP");
            System.exit(1);
            
        }

        if (!this.connect.login(this.user, this.password)) {
            System.out.println("Fallo en la autenticación en el servidor FTP");
            System.exit(1);
        }

    }
}
