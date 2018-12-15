/**
 * CSCI 2113 - Project 2 - Alien Attack
 * 
 * @author [James Won]
 *
 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.lang.Object;
import java.text.DecimalFormat;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.sun.glass.events.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.io.FileReader;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class AlienAttackFrame extends JFrame
{
    private GameBoard gameBoard;
    private Human user;
    private Timer timer;
    private JLabel scoreBoard;
    private JTextArea highScoreBoard;
    private JPanel menuBoard;
    private JButton startButton;
    private JButton pauseButton;
    private ActionListener buttonListener;
    private int counter;
    private float timerCounter;
    private int sum;
    private ArrayList<Alien> alienList;
    private ArrayList<String> scoreList;
    private int timerVal;
    private int timerDelay;
    private int minAlien;
    private int maxAlien;
    private int increaseMax;
    private int increaseMin; 
    private int interval;
    private int largeAlienSpeed;
    private int medAlienSpeed;
    private int smallAlienSpeed;
    private int largeAlienPoint;
    private int medAlienPoint;
    private int smallAlienPoint;
    
    public AlienAttackFrame()
    {
        setTitle("Alien Attack!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        //adding gameBoard, User, start and pause Button, alienlist
        gameBoard = new GameBoard();
        user = new Human();
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        alienList = new ArrayList<Alien>();

        //initialize timerdelay
        timerDelay = 0;
        
        try
        {
            //initialize gameSetting
            setGameSettings();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        //initialize score board and menu board
        scoreBoard = new JLabel();
        scoreBoard.setFont(new Font("Courier",Font.BOLD, 18));
        scoreBoard.setPreferredSize(new Dimension(900,50));
        
        menuBoard = new JPanel();
        menuBoard.setPreferredSize(new Dimension(200, 900));
        highScoreBoard = new JTextArea("High Score: \n");
        highScoreBoard.setEditable(false);
        highScoreBoard.setFocusable(false);
        highScoreBoard.setFont(new Font("Courier", Font.BOLD, 18));
        add(menuBoard);
        
        
        scoreList = new ArrayList<String>();
        try
        {
            //adds to the score list arraylist
            addHighScore();
        }
        catch(IOException io)
        {
            io.printStackTrace();
        }

        //adds the high score to the jframe
        for(int i = 0; i < scoreList.size(); i++)
        {
            highScoreBoard.append(scoreList.get(i) + "\n");
        }

        //adds action listeners to the buttons
        buttonListener = new ButtonListener();
        startButton.addActionListener(buttonListener);
        pauseButton.addActionListener(buttonListener);
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        startButton.setFocusable(false);
        pauseButton.setFocusable(false);
        menuBoard.add(startButton);
        menuBoard.add(pauseButton);
        menuBoard.add(highScoreBoard);

        add(gameBoard);
        gameBoard.add(user);
        add(scoreBoard, BorderLayout.SOUTH);
        add(menuBoard, BorderLayout.EAST);
        
        pack();
        setVisible(true);      
        
        ActionListener timerAction = new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e) 
            {   
                //timerval is a counter for when to increase max and min aliens
                timerVal += 150;
                //timerCounter used for printing to screen the time
                timerCounter += 0.2;
                DecimalFormat df = new DecimalFormat("###.##");
                //if the timerval is greater than or equal to the interval
                //increase the max and min, reset the timerval
                if(timerVal > interval)
                {
                    maxAlien += increaseMax;
                    minAlien += increaseMin;
                    timerVal = 0;
                }
                //if the arraylist of alien is smaller than minAlien
                //then increase alien
                while(alienList.size() <= minAlien)
                {
                    Alien newAlien = new Alien();
                    alienList.add(newAlien);
                    gameBoard.add(newAlien);
                    if(newAlien.getrSize() == 90)
                    {
                        newAlien.setSpeed(largeAlienSpeed);
                        newAlien.setpoints(largeAlienPoint);
                    }
                    if(newAlien.getrSize() == 60)
                    {
                        newAlien.setSpeed(medAlienSpeed);
                        newAlien.setpoints(medAlienPoint);
                    }
                    if(newAlien.getrSize() == 30)
                    {
                        newAlien.setSpeed(smallAlienSpeed);
                        newAlien.setpoints(smallAlienPoint);
                    }
                    timerDelay = 0;
                }
                //while the array list of aliens is less than the max add aliens
                while(alienList.size() <= maxAlien)
                {
                    Alien newAlien = new Alien();
                    alienList.add(newAlien);
                    gameBoard.add(newAlien);
                    if(newAlien.getrSize() == 90)
                    {
                        newAlien.setSpeed(largeAlienSpeed);
                        newAlien.setpoints(largeAlienPoint);
                    }
                    if(newAlien.getrSize() == 60)
                    {
                        newAlien.setSpeed(medAlienSpeed);
                        newAlien.setpoints(medAlienPoint);
                    }
                    if(newAlien.getrSize() == 30)
                    {
                        newAlien.setSpeed(smallAlienSpeed);
                        newAlien.setpoints(smallAlienPoint);
                    }
                    timerDelay = 0;
                }
                
                Alien tempAlien = new Alien();
                //updates the aliens graphics and list
                for(int i = 0; i < alienList.size();i++)
                {
                    tempAlien = alienList.get(i);
                    tempAlien.drop();
                    //deletes aliens that are off the board
                    //and delete them from the list
                    if(tempAlien.get_y()  >= 900)
                    {
                        sum += tempAlien.get_points();
                        scoreBoard.setText(" Score: " + sum + "     Time: " + df.format(timerCounter));
                        gameBoard.remove(tempAlien);
                        alienList.remove(i);
                    }
                }
                
                repaint();
                //checks if the user is hit
                if(hit(user) == true)
                {
                    //reduce size of user
                    counter++;
                    //if the user is hit three times then stop the game
                    if(counter == 3)
                    {
                        timer.stop();
                        JOptionPane.showMessageDialog(null, "Game Over...");
                        try
                        {
                            //updates the highscore
                            highScore();
                        }
                        catch(IOException io)
                        {
                            io.printStackTrace();
                        }
                        
                    }
                }
                scoreBoard.setText(" Score: " + sum + "     Time: " + timerCounter);
            }
        };
        //creates the timer
        timer = new Timer(timerDelay, timerAction);

        addKeyListener(new KeyListener()
        {
        
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) 
            {
                
            }
        
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) 
            {
                
            }
        
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) 
            {
                int keyCode = e.getKeyCode();
                if(keyCode == KeyEvent.VK_LEFT)
                {
                    //moves the user left
                    user.move_left(-1);
                }
                if(keyCode == KeyEvent.VK_RIGHT)
                {

                    //moves the user right
                    user.move_right(1);
                }
            }
        });
    }

     
    public boolean hit(Human player)
    {
        Alien alien = new Alien();
        //goes through the alien list and creates a square at the location of the alien 
        //and checks if it intersects the users square
        for(int i = 0; i < alienList.size(); i++)
        {
            alien = alienList.get(i);
            Rectangle2D alienShape = new Rectangle2D.Double();
            alienShape.setFrame(alien.get_x(), alien.get_y(), alien.getrSize(), alien.getrSize());
            Shape user = new Rectangle(player.get_X(), player.get_Y(), player.getHumanSize(), player.getHumanSize());
            //if it intersects then reduce the user size
            if(user.intersects(alienShape) == true)
            {
                player.reduceSize(30);
                //if the user is hit three times, remove the user from board
                if(player.getHumanSize() == 0)
                {
                    gameBoard.remove(player);
                }
                //remove the alien that hit the user from board and list
                alienList.remove(i);
                gameBoard.remove(alien);
                return true;
            }
        }
        return false;
    }

    public void highScore() throws IOException
    {
        try
        {
            File f = new File("High_Score.txt");
            
            String score = Integer.toString(sum);
            FileWriter fr = new FileWriter(f, false);
            BufferedWriter bw = new BufferedWriter(fr);
            //writes the first line in the file
            if(scoreList.isEmpty() == true)
            {
                bw.write(score);
                bw.close();
                return;
            }
            else
            {
                //checks to see where the new score goes in the list
                //adds it to the list
                for(int i = 0; i < scoreList.size(); i++)
                {
                    String temp = scoreList.get(i); 
                    int num = Integer.parseInt(temp);
                    //if the new score is larger then the compared one, insert before it in the list
                    if(sum > num)
                    {
                        scoreList.add(i, score);
                        //if the scorelist is bigger than 10 scores, remove so its only 10
                        if(scoreList.size() >= 11)
                        {
                            scoreList.remove(scoreList.size()-1);
                        }
                        break;
                    }
                    //if its the last score and to the end
                    if(i == scoreList.size()-1)
                    {
                        scoreList.add(i+1, score);
                        System.out.println(scoreList.size());
                        //check the size again
                        if(scoreList.size() >= 11)
                        {
                            scoreList.remove(scoreList.size()-1);
                        }
                        break;
                    }
                }
                //writes to the new file
                for(int i = 0; i < scoreList.size(); i++)
                {
                    bw.write(scoreList.get(i));
                    bw.newLine();
                }
            }
            bw.close();
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setGameSettings() throws IOException
    {
        try
        {
            //reads the properties
            File file = new File("AlienAttack.properties");
            
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            int[] settings = new int[13];
            int i = 0;
            while(line != null)
            {
                //gets the numbers and adds to the array
                String[] str = line.split("= ");
                settings[i] = Integer.parseInt(str[1]);
                line = br.readLine();
                ++i;
            }
            //sets the variable to respective values
            timerDelay = settings[0];
            user.setSpeed(settings[1]);
            largeAlienSpeed = settings[2];
            medAlienSpeed = settings[3];
            smallAlienSpeed = settings[4];
            interval = settings[5];
            maxAlien = settings[6];
            minAlien = settings[7];
            increaseMax = settings[8];
            increaseMin = settings[9];
            largeAlienPoint = settings[10];
            medAlienPoint = settings[11];
            smallAlienPoint = settings[12];

            
            br.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        

    }

    public void addHighScore() throws IOException
    {
        try
        {
            File f = new File("High_Score.txt");
            //checks to see if the file exists
            if(f.exists())
            {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line = br.readLine();
                //adds the past scores to the scorelist
                while(line != null)
                {
                    scoreList.add(line);
                    line = br.readLine();
                }
                br.close();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
    }

    class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Object obj = e.getSource();
            if(obj == startButton)
            {
                timer.start();
                
                pauseButton.setEnabled(true);
                startButton.setEnabled(false);
            }
            else if(obj == pauseButton)
            {
                timer.stop();
                
                pauseButton.setEnabled(false);
                startButton.setEnabled(true);
            }
            
        }
    }

    
}