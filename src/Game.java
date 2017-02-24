import javafx.geometry.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by hiltjar000 on 2/7/2017.
 */
public class Game extends JPanel implements ActionListener, KeyListener{

    Timer timer;
    private int positionX, positionY, playerDiameter;
    int level = 1;

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
        frame.addKeyListener(this);
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
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }
        });


        frame.pack();
        frame.setLocationRelativeTo(null);

    }

    //TIMER ACTION
    int count = 0;
    @Override
    public void actionPerformed(ActionEvent e){
        if (Stats.isSpacePressed()){
            Stats.setMenu(false);
            Stats.setPause(false);
            Stats.setGame(true);
            setCursor(getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), null));
        }
        if (Stats.ispPressed()){
            Stats.setPause(true);
            Stats.setGame(false);
            setCursor(Cursor.getDefaultCursor());
        }




        if (Stats.isGame()) {
            boolean foodleft = false;
            collision();
            entities.get(0).playerMove();
            for (int i = 1; i < entities.size(); i++) {
                if (entities.get(i) instanceof Food) {
                    foodleft = true;
                }
                if (entities.get(i) instanceof Triangle) {
                    entities.get(i).track(entities.get(0));
                }
                if (entities.get(i) instanceof Torpedo) {

                    if (entities.get(i).checkOutBound()) {
                        entities.remove(i);
                        i--;
                    }

                }
                entities.get(i).move();
            }
            if (!foodleft) {
                //Delay before adding new enemies
                if (count == 80) {
                    level++;
                    count = 0;
                    levels();
                }
                else{count++;}
            }
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
        //removes all entities
        for(int i = 1; i < entities.size(); i++){
            ArrayList<Entity> entCopy = new ArrayList<Entity>(entities);
            if (entities.get(i) instanceof Turret || entities.get(i) instanceof Triangle){}
            else entities.remove(i);
            Entity ent = entCopy.get(i);

            if (ent instanceof Circle && ent.getColor().equals(new Color(255,130,0))){
                entities.add(Circle.fatCircle(this));
            }
            else if (ent instanceof Circle){
                entities.add(Circle.fastCircle(this));
            }

            //entities.add(entCopy.get(i));
        }

        //5 FOOD every level
        for (int i = 0; i < 5; i++) {
            entities.add(Food.normalFood(getHeight(), getWidth(), this));}

        //4 FAT CIRCLES
        if (level == 1) {
            for (int i = 0; i < 4; i++) {
                entities.add(Circle.fatCircle(this));
            }
        }

        if (level == 2){
            entities.add(Circle.fastCircle(this));
        }//every 2 levels

        if(level == 3){
            entities.add(Triangle.tracking(this));
            entities.add(Turret.makeTurret(this));
        }//lvl 3 only

        if(level > 2){
            entities.add(Circle.fastCircle(this));
        }

        if(level > 3){
            entities.add(Turret.makeTurret(this));
        }



        if (level == 21) {
            //game win

        }
    }

    public void collision(){
        Entity player = entities.get(0);

        //PLAYER COLLISIONS
        for (int i = 1; i < entities.size(); i++){
            Entity other = entities.get(i);
            if (player.collides(other)){
                //FOOD
                if(other instanceof Food){
                    //adding health
                    if (player.getHealth() < 125) {
                        player.gainHealth();
                    }
                    entities.remove(i);
                }
                //COLLISIONS WITH ENEMIES
                else if (other instanceof Circle || other instanceof Triangle){
                    //if the player hits behind or opposite to the enemy's direction of motion
                    if (other instanceof Circle){
                        if (player.getX() > other.getX() && other.getSpeed().getX() < 0 || player.getX() < other.getX() && other.getSpeed().getX() > 0) {
                            //GAINS A FOURTH OF PLAYERS SPEED
                            other.addSpeed(new Point3D(player.getSpeed().getX()/4, 0, 0));
                        } else {
                            //LOSES A FOURTH OF ITS SPEED
                            other.addSpeed(new Point3D(player.getSpeed().getX()/-4, 0, 0));
                            other.xBounce();
                        }

                        if (player.getY() > other.getY() + other.getHeight() / 2 && other.getSpeed().getY() < 0 || player.getY() < other.getY() && other.getSpeed().getY() > 0) {
                            //GAINS A FOURTH OF PLAYERS SPEED
                            other.addSpeed(new Point3D(0, player.getSpeed().getY()/4,0));
                        } else {
                            //LOSES A FOURTH OF ITS SPEED
                            other.addSpeed(new Point3D(0, player.getSpeed().getY()/-4, 0));
                            other.yBounce();
                        }
                    }
                    player.loseHealth();
                }
                else if(other instanceof Torpedo){
                    if (player.getShieldHealth() < 5){
                        player.loseHealth(15);
                    }
                    entities.remove(i);
                    i--;
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


        if (Stats.isMenu()){
            setFont(new Font("Times New Roman", Font.BOLD, 32));
            g.setColor(Color.white);
            printSimpleString("Press Space to Play", 0, getWidth()/2, getHeight()/2, g);
        }
        if (Stats.isPause()){
            setFont(new Font("Times New Roman", Font.BOLD, 32));
            g.setColor(Color.white);
            printSimpleString("Press Space to Continue", 0, getWidth()/2, getHeight()/2, g);
        }
        if(Stats.isGame()){
            //PRINTS THE LEVEL
            g.setFont(new Font("Times New Roman", Font.BOLD, 20));
            g.setColor(Color.lightGray);
            printSimpleString("Level: " + level, 100, 0, 52, g);
            printSimpleString("Press P to Pause", 0, getWidth()/2, 25, g);

            for (Entity obj : entities) {
                obj.paint(g);
            }
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

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){Stats.setSpacePressed(true);}
        if(e.getKeyCode() == KeyEvent.VK_P){Stats.setpPressed(true);}

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){Stats.setSpacePressed(false);}
        if(e.getKeyCode() == KeyEvent.VK_P){Stats.setpPressed(false);}
    }
}
