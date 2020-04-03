import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DisplayDice extends JPanel {
    public ArrayList<Image> images = new ArrayList<>();
    int count = 0;
    public int numDie = Yahtzee.getNumDice();

    public DisplayDice(Hand hand) {

    }

    /**
     * A component that displays a tiled image
     */
    class ImageComponent extends JComponent {
        private static final int DEFAULT_WIDTH = 1100;
        private static final int DEFAULT_HEIGHT = 500;

        private Image image1, image2, image3, image4, image5, image6;

        public ImageComponent() {
            image1 = new ImageIcon("1up.png").getImage();
            images.add(count, image1);
            count++;
            image2 = new ImageIcon("2up.png").getImage();
            images.add(count, image2);
            count++;
            image3 = new ImageIcon("3up.png").getImage();
            images.add(count, image3);
            count++;
            image4 = new ImageIcon("4up.png").getImage();
            images.add(count, image4);
            count++;
            image5 = new ImageIcon("5up.png").getImage();
            images.add(count, image5);
            count++;
            image6 = new ImageIcon("6up.png").getImage();
            images.add(count, image6);
            count++;
        }

        public void paintComponent(Graphics g) {
            if (image1 == null) return;

            int image1Width = image1.getWidth(null);
            int image1Height = image1.getHeight(null);

            // draw the image in the upper-left corner

            g.drawImage(image1, 50, 100, null);
            g.drawImage(image2, 70 + image1Width, 100, null);
            g.drawImage(image3, 90 + image1Width * 2, 100, null);

        }
        public Dimension getPreferredSize() { return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT); }
    }
}
