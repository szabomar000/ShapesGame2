/**
 * Created by Jared H on 2/22/2017.
 */
public class Stats {

    public static boolean menu = true, game = false, pause = false;
    public static boolean spacePressed = false, pPressed = false;

    public static boolean isMenu() {return menu;}
    public static void setMenu(boolean menu) {Stats.menu = menu;}
    public static boolean isGame() {return game;}
    public static void setGame(boolean game) {Stats.game = game;}
    public static void toggleGame(){
        if (game){
            game = false;
        }
        else{game = true;}
    }
    public static boolean isPause() {return pause;}
    public static void setPause(boolean pause) {Stats.pause = pause;}
    public static void togglePause(){
        if (pause){
            pause = false;
        }
        else{pause = true;}
    }

    public static boolean isSpacePressed() {return spacePressed;}
    public static void setSpacePressed(boolean spacePressed) {Stats.spacePressed = spacePressed;}
    public static boolean ispPressed() {return pPressed;}
    public static void setpPressed(boolean pPressed) {Stats.pPressed = pPressed;}
}
