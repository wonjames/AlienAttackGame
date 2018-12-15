import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.util.Random;

import javax.lang.model.util.ElementScanner6;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class Alien extends JComponent
{
    private int size = randSize();
    private int speed;
    private int points;
    private int rSize;
    private int x;
    private int y = -90;
    public Alien()
    {
        super();
        
        setSize(size, size);
        setLocation(randX(), y);
    }

    public int randX()
    {
        Random rand = new Random();
        x = rand.nextInt(860) + 0;
        
        return x;
    }

    public void drop()
    {
        y += speed;
        setLocation(x,y);
    }

    public int randSize()
    {
        Random r = new Random();
        rSize = r.nextInt(3)+0;
        if(rSize == 0)
        {
            size = 90;
            return size;
        }
        else if(rSize == 1)
        {
            size = 60;
            return size;
        }
        else if(rSize == 2)
        {
            size = 30;
            return size;
        }
        return size;
    }
    public int getrSize()
    {
        return size;
    }

    public void setpoints(int point)
    {
        points = point;
    }

    public void setSpeed(int s)
    {
        speed = s;
    }

    public int get_x()
    {
        return x;
    }

    public int get_y()
    {
        return y;
    }

    public int get_points()
    {
        return points;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color green = new Color(0, 153, 0);
        g2d.setColor(green);
        g2d.fillRect(0, 0, size, size);
    }
}