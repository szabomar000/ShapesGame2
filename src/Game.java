import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by hiltjar000 on 2/7/2017.
 */
public class Game extends JPanel implements ActionListener{

    Timer timer;
    private int positionX, positionY, playerDiameter;
    int level = 2;

    ArrayList<Entity> entities;

    public Game(){
        //SETS UP JFRAME
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("SHAPES");
        setPreferredSize(new Dimension(600,800));
        setBackground(Color.black);

        /*
        ImageIcon Pic = new ImageIcon("./src/Icon.png");
        Image icon = Pic.getImage();
        frame.setIconImage(icon);
         */

        frame.add(this);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e){
                super.mouseMoved(e);
                positionX = e.getX();
                positionY = e.getY();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(getToolkit().createCustomCursor(new BufferedImage(3,3, BufferedImage.TYPE_INT_ARGB), new Point(0,0), null));
            }
        });
        frame.pack();
        frame.setLocationRelativeTo(null);

    }

    //TIMER ACTION
    int count = 0;
    @Override
    public void actionPerformed(ActionEvent e){
        boolean foodleft = false;
        collision();
        entities.get(0).playerMove();
        for (int i = 1; i < entities.size(); i++){
            if (entities.get(i) instanceof Food){
                foodleft = true;
            }
            if (entities.get(i) instanceof Triangle){
                entities.get(i).track(entities.get(0));
            }
            entities.get(i).move();
        }
        if (!foodleft){
            count++;
        }
        if(count == 40){
            level++;
            count = 0;
            levels();
        }
        repaint();
    }


    public void init(){
        //CONTROLLER LIST
        playerDiameter = 30;
        entities = new ArrayList<Entity>();
        entities.add(new Player(Color.MAGENTA, getWidth()/2, getHeight()/2, playerDiameter,this));
        levels();

    }

    //CALL LEVELS WHENEVER THE LEVEL IS OVER
    public void levels(){

        //10 FOOD every level
        for (int i = 0; i < 10; i++) {
            entities.add(Food.normalFood(getHeight(), getWidth(), this));

        }


        //4 FAT CIRCLE
        if (level == 1) {
            for (int i = 0; i < 4; i++) {
                entities.add(Circle.fatCircle(this));
            }
        }
        //5 FAT CIRCLE
        if (level == 2){
            entities.add(Circle.fatCircle(this));
            entities.add(Triangle.tracking(getWidth(), getHeight(), entities.get(0), this));
            entities.add(Turret.makeTurret(this));
            entities.add(Torpedo.torp(this));
            for (int i = 0; i < 2; i++) {
                entities.add(Circle.fastCircle(this));
            }
        }
    }

    public void collision(){

        for (int i = 0; i < entities.size(); i++){
            for (int j = 1; j < entities.size(); j++){

                //PLAYER COLLISIONS
                if (i == 0){
                    Entity player = entities.get(0);
                    Entity other = entities.get(j);
                    if (player.collides(other)){
                        //FOOD
                        if(other instanceof Food){
                            //adding health
                            if (player.getHealth() < 125) {
                                player.gainHealth();
                            }
                            entities.remove(j);
                        }
                        //COLLISIONS WITH ENEMIES
                        else if (other instanceof Circle || other instanceof Triangle){
                            //if the player hits behind or opposite to the enemy's direction of motion
                            if (other instanceof Circle){
                                if (player.getX() > other.getX() && other.getDx() < 0 || player.getX() < other.getX() && other.getDx() > 0) {
                                    //GAINS A FOURTH OF PLAYERS SPEED
                                    other.addSpeed(player.getDx() / 4.0, 0);
                                } else {
                                    //LOSES A FOURTH OF ITS SPEED
                                    other.addSpeed(player.getDx() / -4.0, 0);
                                    other.xBounce();
                                }

                                if (player.getY() > other.getY() + other.getHeight() / 2 && other.getDy() < 0 || player.getY() < other.getY() && other.getDy() > 0) {
                                    //GAINS A FOURTH OF PLAYERS SPEED
                                    other.addSpeed(0, player.getDy() / 4.0);
                                } else {
                                    //LOSES A FOURTH OF ITS SPEED
                                    other.addSpeed(0, player.getDy() / -4.0);
                                    other.yBounce();
                                }
                            }
                            player.loseHealth();
                        }
                    }
                }
            }
        }
    }

    public void run(){
        timer = new Timer(1000/60, this);
        timer.start();
    }

    public void paint(Graphics g){
        super.paint(g);

        //PRINTS THE LEVEL
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        printSimpleString("Level: " + level, 100, 0, 50, g);



        for( Entity obj : entities){
            obj.paint(g);
        }
    }
    public static void main(String[] args){
        Game game = new Game();
        game.init();
        game.run();
    }

    //WHEN THE PLAYER LOSES THE GAME (HEALTH = 0)
    public void lose(){
        JOptionPane.showMessageDialog(null, "You Lose.");
        System.exit(0);
    }

    private void printSimpleString(String s, int width, int XPos, int YPos, Graphics g2d){

        int stringLen = (int)g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int start = width/2 - stringLen/2;

        g2d.drawString(s, start + XPos, YPos);

    }


    //GETTERS FOR MOUSE POSITION
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Point randomPosition(){
        return new Point( (int) (25 + (getWidth() - 50) * Math.random()),(int) (25 + (getHeight() - 50) * Math.random()));
    }

    public int getPlayerDiameter() {
        return playerDiameter;
    }
}
