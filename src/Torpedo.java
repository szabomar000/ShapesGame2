import javafx.geometry.Point3D;

import java.awt.*;

/**
 * Created by szabomar000 on 2/17/2017.
 */
public class Torpedo extends Entity {
    double angle;

    public Torpedo(Color color, int x, int y, int width, int height, double speed, double angle, Game game){
        super(color, x, y, width, height, speed, speed, game);
        this.angle=angle;
        setSpeed(new Point3D(Math.round(Math.cos(angle)*speed), Math.round(Math.sin(angle)*speed), speed));
    }

    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getColor());
        g2d.rotate(angle, getX(), getY());
        g2d.fillRect(getX(), getY(), getWidth(), getHeight());
        g2d.dispose();
    }

    public static Torpedo torp(int x, int y, double angle, Game game){
        return new Torpedo(Color.red, x, y, 10, 5, 3, angle, game);
    }

    @Override
    public void move(){
        setX(getX()+(int)getSpeed().getX());
        setY(getY()+(int)getSpeed().getY());
    }




}
