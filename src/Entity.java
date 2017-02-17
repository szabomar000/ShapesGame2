import java.awt.*;

/**
 * Created by hiltjar000 on 2/7/2017.
 */
public abstract class Entity {
    
    //This was made via Internets

    private Game game;
    private Color color;
    private int x, y, width, height, pastX, pastY;
    private double minSpeed, maxSpeed, angle;
    private Point speed;
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
        double nextLeft = x + speed.getX();
        double nextRight = x + width + speed.getX();
        double nextTop = y + speed.getY();
        double nextBottom = y + height + speed.getY();

        if(nextTop <=0 || nextBottom > game.getHeight()) {
            yBounce();
        }
        if (nextLeft <= 0 || nextRight > game.getWidth()) {
            xBounce();
        }
        x+=speed.getX();
        y+=speed.getY();

    }

    //"SLOWING" OVER TIME 
    public void addSpeed(Point speed){
        if(this.speed.getX()+speed.getX() < maxSpeed){
            this.speed.x += speed.getX();
        }
        if(this.speed.getY()+speed.getY() < maxSpeed) {
            this.speed.y += speed.getY();
        }

    }

    public void yBounce(){
        //SLOWS SPEED OVER TIME
        if (speed.getY() > minSpeed){
            speed.y -= speed.getY()/16;} speed.y*=-1;
        updateVector();
    }
    public void xBounce(){
        //SLOWS SPEED OVER TIME
        if (speed.getX() > minSpeed){
            speed.x -= speed.getX()/16;} speed.x*=-1;
        updateVector();
    }

    private void updateVector(){
        angle = Math.tan(speed.getY()/speed.getX());
    }


    //PLAYER'S MOVE METHOD
    public void playerMove(){
        speed.x = Math.abs(pastX-game.getPositionX());
        speed.y = Math.abs(pastY-game.getPositionY());
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


    public void setDx(int dx) {
        speed.x = dx;
    }

    public void setDy(int dy) {
        speed.y = dy;
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

    public Point getSpeed() {
        return speed;
    }

    public double getAngle() {
        return angle;
    }

    //USE FOR TRACKING ENEMIES
    public void track(Entity player){
        //NEED TO REDO
    }

    //CREATES A RANDOM SPEED AND ANGLE
    public void createSpeed(){
        angle = 2 * Math.PI * Math.random();
        spd = minSpeed + Math.random()*(maxSpeed-minSpeed);

        setDx(Math.cos(angle) );
        setDy(Math.sin(angle) * speed);

    }

}

