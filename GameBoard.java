import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class GameBoard extends JPanel
{
   
   protected static int boardSize = 900;
   protected static int gridSize = 30;
   protected static int numGrids = 30;   
   private int x;
   public GameBoard()
   {
      setBorder(BorderFactory.createLineBorder(Color.black));
      setBackground(new Color(197, 2, 81, 1));
      setLayout(null);
      setSize(900, 900);
      
   }
   public int randX()
   {
      Random rand = new Random();
      x = rand.nextInt(800) + 0;
      
      return x;
   }

   @Override
   public Dimension getPreferredSize()
   {
      return new Dimension(boardSize, boardSize);
   }

   @Override
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
      BufferedImage img = null;
      try 
      {
          img = ImageIO.read(new File("starsGalaga.jpg"));
      } 
      catch (IOException e) 
      {
          e.printStackTrace();
      }
      g2d.drawImage(img, 0, 0, null);
   }

   
}
