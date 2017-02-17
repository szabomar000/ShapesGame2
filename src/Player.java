import java.awt.*;

/**
 * Created by hiltjar000 on 2/10/2017.
 */
public class Player extends Circle{


    public Player(Color color, int x, int y, int diameter, Game game) {
        super(color, x, y, diameter, 0, 0, game);


    }

    @Override
    public void paint(Graphics g){
        int d= ((health+32)/4)+10;

        //HEALTH BAR
        float hue = (float)health/400f;
        g.setColor(Color.getHSBColor(hue, 1, 1));
        g.fillRect(10,10, health, 20);

        //SHIELD AROUND HEALTH BAR
        g.setColor(Color.BLUE);
        for (int i = 0; i < shieldHealth/4; i++){
            g.drawRect(10-(i+1), 10-(i+1), health+2*(i+1),20+2*(i+1) );
        }

        g.setColor(Color.MAGENTA);
        g.fillOval(getX(), getY(), d, d);
        
        //VISUAL SHIELD AROUND THE PLAYER

        g.setColor(Color.CYAN);
        for(double i = 0; i < shieldHealth/4; i++){
            g.drawOval(getX()+(int)i, getY()+(int)i, d-2*(int)i, d-2*(int)i);
        }

    }

}
