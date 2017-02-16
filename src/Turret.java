import java.awt.*;

/**
 * Created by szabomar000 on 2/15/2017.
 */
public class Turret extends Entity{
    private int x, y, width, height;
    private Game game;


    public Turret(int x ,int y, int width, int height, Game game){
        super(Color.RED, x, y, width, height, 0,0, game);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.game = game;


    }


    public void paint(Graphics g){
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);

        g.setColor(Color.darkGray);
        g.fillOval(x+width/4, y+height/4, width/2, height/2);
    }

    public static Turret makeTurret( Game game){
        Point point = game.randomPosition();
        return new Turret((int) point.getX(),(int) point.getY(), 20, 20, game);
    }





}
