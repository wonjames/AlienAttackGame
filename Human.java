import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javafx.geometry.Rectangle2D;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Human extends JComponent
{
    private int size = 90;
    private int x = 300;
    private int y = 810;
    private int speed;
    public Human() 
    {
        super();
        setSize(size, size);
        setLocation(300, 810);
    }

    public void move_left(int i)
    {
        if(x <= 0)
        {
            return;
        }
        
        x += i * speed;
        setLocation(x, y);
    }

    public void move_right(int i)
    {
        if(size == 90)
        {
            if(x >= 810)
            {
                return;
            }
            x += i * speed;
        }
        else if(size == 60)
        {
            if(x >= 840)
            {
                return;
            }
            x += i * speed;
        }
        else if(size == 30)
        {
            if(x >= 870)
            {
                return;
            }
            x += i * speed;
        }
        setLocation(x, y);
    }
    public void setSpeed(int x)
    {
        speed = x;
    }

    public int getSpeed()
    {
        return speed;
    }

    public int get_X()
    {
        return x;
    }

    public int get_Y()
    {
        return y;
    }

    public int getHumanSize()
    {
        return size;
    }

    public void reduceSize(int num)
    {
        size -= num;
        setSize(size, size);
        if(size == 60)
        {
            y = 840;
            setLocation(x,y);
            return;
        }
        else if(size == 30)
        {
            y = 870;
            setLocation(x,y);
            return;
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, size, size);
    }
    
}