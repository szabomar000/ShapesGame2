import java.awt.*;

/**
 * Created by hiltjar000 on 2/7/2017.
 */
public abstract class Entity {
    
    //This was made via Internets

    private Game game;
    private Color color;
    private int x, y, width, height, pastX, pastY;
    private double dx, dy, minSpeed, maxSpeed, speed, angle;
    int shieldHealth;
    int health;




    public Entity(Color color, int x, int y, int width, int height, double minSpeed, double maxSpeed, Game game) {
        this.game = game;
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        health = 100;
        shieldHealth = 20;


    }

     //GENERIC MOVE METHOD
    public void move(){
        double nextLeft = x + dx;
        double nextRight = x + width + dx;
        double nextTop = y + dy;
        double nextBottom = y + height + dy;

        if(nextTop <=0 || nextBottom > game.getHeight()) {
            //SLOWS AFTER EVERY BOUNCE
            dy -= dy/25;
            yBounce();
        }
        if (nextLeft <= 0 || nextRight > game.getWidth()) {
            //SLOWS AFTER EVERY BOUNCE
            dx -= dx/25;
            xBounce();
        }
        x+=dx;
        y+=dy;

    }
    public void addSpeed(double dx, double dy){
        if(this.dx+dx < maxSpeed){
            this.dx += dx;
        }
        if(this.dy+dy < maxSpeed) {
            this.dy += dy;
        }

    }

    public void yBounce(){
        //SLOWS SPEED OVER TIME
        if (dy > minSpeed){
            dy -= dy/16;} dy*=-1;
        updateVector();
    }
    public void xBounce(){
        //SLOWS SPEED OVER TIME
        if (dx > minSpeed){
            dx -= dx/16;} dx*=-1;
        updateVector();
    }

    private void updateVector(){
        speed = Math.pow(dx*dx + dy*dy, 1/2);
        angle = Math.tan(dy/dx);
    }

    public void bounce(){
        yBounce();
        xBounce();
    }

    public void playerMove(){
        dx = Math.abs(pastX-game.getPositionX());
        dy = Math.abs(pastY-game.getPositionY());
        setX(game.getPositionX());
        setY(game.getPositionY());
        pastX = game.getPositionX();
        pastY = game.getPositionY();

    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public boolean collides(Entity other){
        if (getBounds().intersects(other.getBounds())){
            return true;
        }
        return false;
    }

    public void gainHealth(){
        health+=5;
    }

    public void loseHealth() {
        if (shieldHealth > 0) {
            shieldHealth-=1;
        } else if (health > 1) {
            health-=3;
        } else {
            game.lose();
        }
    }

    public abstract void paint(Graphics g);

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public int getShieldHealth() {
        return shieldHealth;
    }

    public void setShieldHealth(int shieldHealth) {
        this.shieldHealth = shieldHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getMinSpeed() {
        return minSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getSpeed() {
        return speed;
    }

    public double getAngle() {
        return angle;
    }

    public void track(Entity player){

        if (player.getX() > getX()){
            setDx(getSpeed());
        }
        else if (player.getX() < getX()){
            setDx(getSpeed()*-1);
        }
        else{
            setDx(0);
        }

        if (player.getY() > getY()){
            setDy(getSpeed());
        }
        else if(player.getY() < getY()){
            setDy(getSpeed()*-1);
        }
        else{
            setDy(0);
        }
    }

    public void createSpeed(){
        angle = 2 * Math.PI * Math.random();
        speed = minSpeed + maxSpeed * Math.random();
        setDx(Math.cos(angle) * speed);
        setDy(Math.sin(angle) * speed);

    }
}

