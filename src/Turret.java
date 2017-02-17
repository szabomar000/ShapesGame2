import java.awt.*;

/**
 * Created by szabomar000 on 2/15/2017.
 */
public class Turret extends Entity{

    double angle;
    public Turret(int x ,int y, int width, int height, Game game){
        super(Color.RED, x, y, width, height, 0,0, game);
        angle = 0;


    }


    public void paint(Graphics g){
        g.setColor(Color.red);
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        g.setColor(Color.darkGray);
        g.fillOval(getX()+getWidth()/4, getY()+getHeight()/4, getWidth()/2, getHeight()/2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(angle, getX()+getWidth()/2, getY()+getHeight()/2);
        g2d.fillRect(getX()+getWidth()/2, getY()+5*getHeight()/12, getWidth()/2, getHeight()/4);

        updateAngle();

    }

    public static Turret makeTurret( Game game){
        Point point = game.randomPosition();
        return new Turret((int) point.getX(),(int) point.getY(), 30, 30, game);
    }

    public void updateAngle(){
        int cX = (getGame().getPositionX()+getGame().getPlayerDiameter()/2)-(getX()+getWidth()/2);
        int cY = (getGame().getPositionY()+getGame().getPlayerDiameter()/2)-(getY()+getHeight()/2);


        angle = Math.atan2((double)cY, (double)cX);
    }





}
