import edu.vadym.vorobel.ImageGenerator;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by Vadym on 10.01.2016.
 */
public class ImageGeneratorTest {

  static {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

  public static void main(String[] args) {
    ImageGenerator imageGenerator = new ImageGenerator(new Size(1024, 768), 10);
    Mat generatedImage = imageGenerator.generate();
    displayImage(generatedImage, "Generated image");
  }

  public static void displayImage(Mat matImg, String title){
    Image img = Mat2BufferedImage(matImg);

    ImageIcon icon=new ImageIcon(img);
    JFrame frame = new JFrame();
    frame.setSize(img.getWidth(null)+50, img.getHeight(null)+50);
    JScrollPane jsp = new JScrollPane(new JLabel(icon));
    jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    frame.getContentPane().add(jsp);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setTitle(title);
  }

  /*
      Перетворює Mat зображення в BufferedImage
      для того, щоб його можна було показати в JFrame
   */
  public static BufferedImage Mat2BufferedImage(Mat m){
    int type = BufferedImage.TYPE_BYTE_GRAY;
    if ( m.channels() > 1 ) {
      type = BufferedImage.TYPE_3BYTE_BGR;
    }
    int bufferSize = m.channels()*m.cols()*m.rows();
    byte [] b = new byte[bufferSize];
    m.get(0, 0, b); // get all the pixels
    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    System.arraycopy(b, 0, targetPixels, 0, b.length);
    return image;
  }
}
