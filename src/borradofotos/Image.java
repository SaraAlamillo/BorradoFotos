
package borradofotos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * Optimiza todas las imágenes de un directorio recursivamente
 * @author Sara Alamillo Arroyo
 */
public class Image {
    public void optimize(String path) throws IOException {
        this.optimize(path, 0.8f);
    }
    public void optimize(String path, float quality) throws IOException {
        
        File input = new File(path);
        BufferedImage image = ImageIO.read(input);

        File compressedImageFile = new File(path);
        ImageWriter writer;
        ImageOutputStream ios;
                
        try (OutputStream os = new FileOutputStream(compressedImageFile)) {
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            writer = (ImageWriter) writers.next();
            ios = ImageIO.createImageOutputStream(os);
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            writer.write(null, new IIOImage(image, null, null), param);
        }
        
        ios.close();
        writer.dispose();
    }
}
