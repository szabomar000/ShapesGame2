import java.awt.*;

/**
 * Created by szabomar000 on 2/15/2017.
 */
public class Turret extends Entity{

    public Turret(int x ,int y, int width, int height, Game game){
        super(Color.RED, x, y, width, height, 0,0, game);



    }


    public void paint(Graphics g){
        g.setColor(Color.red);
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        g.setColor(Color.darkGray);
        g.fillOval(getX()+getWidth()/4, getY()+getHeight()/4, getWidth()/2, getHeight()/2);
        g.fillRect(getX()+getWidth()/2, getY()+5*getHeight()/12, getWidth()/2, getHeight()/4);

    }

    public static Turret makeTurret( Game game){
        Point point = game.randomPosition();
        return new Turret((int) point.getX(),(int) point.getY(), 30, 30, game);
    }





}
