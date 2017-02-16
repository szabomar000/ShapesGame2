import java.awt.*;

/**
 * Created by hiltjar000 on 2/7/2017.
 */
public class Food extends Entity{

    public Food(Color color, int x, int y, int width, int height, int minSpeed, int maxSpeed, Game game){
        super(color, x, y, width, height, minSpeed, maxSpeed, game);

    }

    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    //REGULAR FOOD
    public static Food normalFood(int height, int width, Game game){
        return new Food (Color.green, (int) (25 + (width - 50) * Math.random()),(int) (25 + (height - 50) * Math.random()), 25, 18, 5, 6, game);
    }
}
