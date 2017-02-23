import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by szabomar000 on 2/13/2017.
 */
public class Triangle extends Entity {

    double angle;

    public Triangle(Color color,  int x, int y, int base, int height, double minSpeed, double maxSpeed, Game game){
        super(color, x, y, base, height, minSpeed, maxSpeed, game);
        createSpeed();
        track(game.getEntities().get(0));

    }


    @Override
    public void paint(Graphics g) {


        g.setColor(getColor());

        int xPoints[] = {getX()+getWidth()/2, getX(), getX()+getWidth()};
        int yPoints[] = {getY(), getY()+getWidth(), getY()+getWidth()};
        int nPoints = 3;

        g.fillPolygon(xPoints, yPoints, nPoints);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(angle, getX()+getWidth()/2, getY()+getHeight()/2);
        updateAngle();


    }

    public void updateAngle(){
        int cX = (getGame().getPositionX()+getGame().getPlayerDiameter()/2)-(getX()+getWidth()/2);
        int cY = (getGame().getPositionY()+getGame().getPlayerDiameter()/2)-(getY()+getHeight()/2);

        angle = Math.atan2((double)cY, (double)cX);}


    //TRACKING ENEMY
    public static Triangle tracking(Game game){
        Point point = game.randomPosition();
        return new Triangle(Color.yellow, (int) point.getX(),(int) point.getY(), 20, 30, 1.5, 2.2, game);
    }
}
