import java.awt.*;

/**
 * Created by hiltjar000 on 2/7/2017.
 */
public class Circle extends Entity{


    public Circle(Color color, int x, int y, int diameter, double minSpeed, double maxSpeed, Game game){
        super(color, x, y, diameter, diameter, minSpeed, maxSpeed, game);

        //RANDOM SPEED AND ANGLE


    }

    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getX(),getY(),getWidth(),getHeight());


    }

    //THE FAT ENEMY
    public static Circle fatCircle(int height, int width, Game game){
        Point point = game.randomPosition();
        return new Circle(Color.RED, (int) point.getX(),(int) point.getY(), 50, 1.75,3, game);
    }

    public static Circle fastCircle(int height, int width, Game game){
        Point point = game.randomPosition();
        return new Circle(Color.blue, (int) point.getX(),(int) point.getY(), 15,  3, 6, game);
    }
}
