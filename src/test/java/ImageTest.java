import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public class ImageTest {

    @Test
    public void test() throws IOException {
        BufferedImage image = ImageIO.read(new File("d:/1.jpg"));
        assertNotNull(image);

        BufferedImage image2 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
        assertNotNull(image2);

    }
}
