import java.awt.*;

/**
 * Created by hiltjar000 on 2/7/2017.
 */
public class Circle extends Entity{


    public Circle(Color color, int x, int y, int diameter, double minSpeed, double maxSpeed, Game game){
        super(color, x, y, diameter, diameter, minSpeed, maxSpeed, game);
        createSpeed();



    }

    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getX(),getY(),getWidth(),getHeight());


    }

    //THE FAT ENEMY
    public static Circle fatCircle(Game game){

        Point point = game.randomPosition();
        return new Circle(new Color(255,130,0), (int) point.getX(),(int) point.getY(), 50, 2.5,3.5, game);
    }

    //THE FAST ENEMY
    public static Circle fastCircle(Game game){
        Point point = game.randomPosition();
        return new Circle(Color.blue, (int) point.getX(),(int) point.getY(), 25,  3, 6, game);
    }
}
