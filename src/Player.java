import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hiltjar000 on 2/10/2017.
 */
public class Player extends Circle implements ActionListener{

    Timer shieldTimer;
    int count = 0;

    public Player(Color color, int x, int y, int diameter, Game game) {
        super(color, x, y, diameter, 0, 0, game);
        shieldTimer = new Timer(1000/60, this);
        shieldTimer.start();
    }

    @Override
    public void paint(Graphics g){
        int d= ((health+32)/4)+10;

        //HEALTH BAR
        float hue = (float)health/400f;
        g.setColor(Color.getHSBColor(hue, 1, 1));
        g.fillRect(10,10, health, 20);
        g.setColor(Color.white);
        g.drawString(health + "/125", 26, 26);

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

    @Override
    public void actionPerformed(ActionEvent e) {

        if (shieldHealth < 20){
            shieldHealth+=.01;
        }
    }
}
