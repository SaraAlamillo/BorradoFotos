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
    private final List<String> notTouch;
    private int photosDelete;
    private final List<String> photosServer;
    private final String pathPhotos;

    public FTP() throws IOException {

        if (BorradoFotos.local) {
            pathPhotos = "/media/catalog/product";
        } else {
            pathPhotos = "/public_html/media/catalog/product";
        }

        this.notTouch = new ArrayList<>();
        this.notTouch.add("cache");
        this.notTouch.add("thumbs");
        this.notTouch.add("watermark");
        this.notTouch.add(".");
        this.notTouch.add("..");

        this.photosDelete = 0;
        this.photosServer = new ArrayList<>();

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

    public void setPhotosServer() throws IOException {
        this.setPhotosServer(pathPhotos, "");
    }

    public void setPhotosServer(String parentDir, String currentDir) throws IOException {

        String dirToList = parentDir;

        if (!currentDir.equals("")) {
            dirToList += "/" + currentDir;
        }

        FTPFile[] files = this.connect.listFiles(dirToList);

        if (files != null && files.length > 0) {

            for (FTPFile f : files) {

                String fileName = f.getName();

                if (this.notTouch.contains(fileName)) {
                    continue;
                }

                if (f.isDirectory()) {
                    this.setPhotosServer(dirToList, fileName);
                } else {
                    this.photosServer.add(dirToList + "/" + fileName);
                }

            }

        }

    }

    public List<String> getPhotosServer() {
        return this.photosServer;
    }

    public List<String> getOldPhotos(List<String> dbPhotos) {
        List<String> oldPhotos = new ArrayList<>();

        this.photosServer.stream().filter((name) -> (!dbPhotos.contains(name))).forEachOrdered((name) -> {
            oldPhotos.add(name);
        });

        return oldPhotos;
    }

    public int deletePhotos(List<String> photos) throws IOException {

        for (String p : photos) {
            this.connect.deleteFile(p);
            System.out.println("\t-> ...Borrando... " + p);
            this.photosDelete++;
        }

        return this.photosDelete;
    }

    public String getPathPhotos() {
        return this.pathPhotos;
    }
    
    public boolean deleteCache() throws IOException {
        return this.removeDirectory(this.pathPhotos + "/cache", "");
    }
    
    public boolean removeDirectory(String parentDir, String currentDir) throws IOException {
        
        String dirToList = parentDir;
        boolean result = false;
        
        if (!currentDir.equals("")) { dirToList += "/" + currentDir; }
 
        FTPFile[] subFiles = this.connect.listFiles(dirToList);
 
        if (subFiles != null && subFiles.length > 0) {
            
            for (FTPFile aFile : subFiles) {
                
                String currentFileName = aFile.getName();
                
                if (currentFileName.equals(".") || currentFileName.equals("..")) { continue; }
                
                String filePath = parentDir + "/" + currentDir + "/" + currentFileName;
                
                if (currentDir.equals("")) { filePath = parentDir + "/" + currentFileName; }
 
                if (aFile.isDirectory()) { this.removeDirectory(dirToList, currentFileName); } 
                else { this.connect.deleteFile(filePath); }
            }
 
            result = this.connect.removeDirectory(dirToList);
        }
        
        return result;
    }
}
