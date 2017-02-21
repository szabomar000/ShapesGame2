import java.awt.*;

/**
 * Created by szabomar000 on 2/17/2017.
 */
public class Torpedo extends Entity {

    public Torpedo(Color color, int x, int y, int width, int height, double minSpeed, double maxSpeed, Game game){
        super(color, x, y, width, height, minSpeed, maxSpeed, game);
        createSpeed();
    }

    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(),getY(),getWidth(),getHeight());
    }

    public static Torpedo torp(Game game){
        Point point = game.randomPosition();
        return new Torpedo(Color.red, (int) point.getX(), (int) point.getY(), 10, 10, 3, 4, game);
    }




}
