import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by szabomar000 on 2/13/2017.
 */
public class Triangle extends Entity {


    public Triangle(Color color,  int x, int y, int base, int height, double minSpeed, double maxSpeed, Entity player, Game game){
        super(color, x, y, base, height, minSpeed, maxSpeed, game);
        createSpeed();
        track(player);

    }


    @Override
    public void paint(Graphics g) {


        g.setColor(getColor());

        int xPoints[] = {getX()+getWidth()/2, getX(), getX()+getWidth()};
        int yPoints[] = {getY(), getY()+getWidth(), getY()+getWidth()};
        int nPoints = 3;

        g.fillPolygon(xPoints, yPoints, nPoints);


    }


    //TRACKING ENEMY
    public static Triangle tracking(int base, int height, Entity player, Game game){
        Point point = game.randomPosition();
        return new Triangle(Color.yellow, (int) point.getX(),(int) point.getY(), 20, 30, 1.25, 1.75, player, game);
    }
}
