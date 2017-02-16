import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by szabomar000 on 2/13/2017.
 */
public class Triangle extends Entity {


    public Triangle(Color color,  int x, int y, int base, int height, double minSpeed, double maxSpeed, Entity player, Game game){
        super(color, x, y, base, height, minSpeed, maxSpeed, game);
        track(player);

    }


    @Override
    public void paint(Graphics g) {


        g.setColor(getColor());

        int xPoints[] = {getX()+getWidth()/2, getX(), getX()+getWidth()};
        int yPoints[] = {getY(), getY()+getWidth(), getY()+getWidth()};
        int npoints = 3;

        g.fillPolygon(xPoints, yPoints, npoints);


    }



    public static Triangle tracking(int base, int height, Entity player, Game game){
        Point point = game.randomPosition();
        return new Triangle(Color.yellow, (int) point.getX(),(int) point.getY(), 20, 30, 1.5, 2, player, game);
    }
}