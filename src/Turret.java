import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by szabomar000 on 2/15/2017.
 */
public class Turret extends Entity implements ActionListener{

    double angle;
    Timer reload;

    public Turret(int x ,int y, int width, int height, double time, Game game){
        super(Color.RED, x, y, width, height, 0,0, game);
        time *= 1000;
        reload = new Timer((int)time, this);
        angle = 0;
        reload.start();

    }


    public void paint(Graphics g){

        //PAINTS THE BASE
        g.setColor(Color.red);
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        //PAINTS THE CIRCLE
        g.setColor(Color.darkGray);
        g.fillOval(getX()+getWidth()/4, getY()+getHeight()/4, getWidth()/2, getHeight()/2);

        //PAINTS THE "GUN"
        //CREATES A NEW GRAPHICS OBJECT SO THAT NOTHING ELSE GETS ROTATED
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(angle, getX()+getWidth()/2, getY()+getHeight()/2);
        g2d.fillRect(getX()+getWidth()/2, getY()+5*getHeight()/12, getWidth()/2, getHeight()/4);
        updateAngle();
        //GETS RID OF THE NEW GRAPHICS OBJECT
        g2d.dispose();

    }

    public static Turret makeTurret( Game game){
        Point point = game.randomPosition();
        return new Turret((int) point.getX(),(int) point.getY(), 30, 30, 7, game);
    }

    public void updateAngle(){
        int cX = (getGame().getPositionX()+getGame().getPlayerDiameter()/2)-(getX()+getWidth()/2);
        int cY = (getGame().getPositionY()+getGame().getPlayerDiameter()/2)-(getY()+getHeight()/2);


        angle = Math.atan2((double)cY, (double)cX);
    }



    public void actionPerformed(ActionEvent e) {
        System.out.println("I FIRE TORPEDO");
    }
}
