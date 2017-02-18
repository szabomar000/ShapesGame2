import javafx.geometry.Point3D;

import java.awt.*;

/**
 * Created by hiltjar000 on 2/7/2017.
 */
public abstract class Entity {
    
    //This was made via Internets

    private Game game;
    private Color color;
    private int x, y, width, height, pastX, pastY;
    private double minSpeed, maxSpeed, angle, actualSpeed;
    private Point3D speed;
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
        speed = new Point3D(0,0, 0);
        actualSpeed = 0;
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
    public void addSpeed(Point3D speed){
        if(this.speed.getX()+speed.getX() < maxSpeed){
            this.speed.add(speed.getX(),0,0);
        }
        if(this.speed.getY()+speed.getY() < maxSpeed) {
            this.speed.add(0, speed.getY(),0);
        }

    }

    public void yBounce(){
        //SLOWS SPEED OVER TIME
        if (speed.getY() > minSpeed){
            speed.subtract(0, speed.getY()/16, 0);}
            speed=new Point3D(speed.getX(), speed.getY()*-1, speed.getZ());
        updateVector();
    }
    public void xBounce(){
        //SLOWS SPEED OVER TIME
        if (speed.getX() > minSpeed){
            speed.subtract(speed.getX()/16, 0, 0);}
            speed = new Point3D(speed.getX()*-1, speed.getY(), speed.getZ());
        updateVector();
    }

    private void updateVector(){
        angle = Math.tan(speed.getY()/speed.getX());
    }


    //PLAYER'S MOVE METHOD
    public void playerMove(){
        speed = new Point3D(Math.abs(pastX-game.getPositionX()), Math.abs(pastY-game.getPositionY()), 0);
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
        speed = new Point3D(dx, speed.getY(), 0);
    }

    public void setDy(int dy) {
        speed = new Point3D(speed.getX(), dy, 0);
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

    public Point3D getSpeed() {
        return speed;
    }

    public double getAngle() {
        return angle;
    }



    //USE FOR TRACKING ENEMIES
    public void track(Entity player){
        angle = Math.atan2((player.getY()-player.getWidth()/2)-y, (player.getX()-player.getWidth()/2)-x);
        double x = actualSpeed*Math.cos(angle);
        double y = actualSpeed*Math.sin(angle);
        speed = new Point3D(x, y, getHypotenuse(x, y));
        System.out.println("Speed: " + x + " " + y);
        System.out.println("Vector Speed: " + actualSpeed);
        System.out.println("Angle: " + angle/Math.PI*180);
        System.out.println("sin: " + Math.sin(angle));

    }

    public double getHypotenuse(double x, double y){
        return Math.pow(Math.pow(x, 2) + Math.pow(y, 2),1/2);
    }

    //CREATES A RANDOM SPEED AND ANGLE
    public void createSpeed(){
        angle = 2 * Math.PI * Math.random();
        actualSpeed = minSpeed + Math.random()*(maxSpeed-minSpeed);

        double x = Math.cos(angle) * actualSpeed;
        double y = Math.sin(angle) * actualSpeed;
        setSpeed(new Point3D(x, y, speed.getZ()));


    }

    public void setSpeed(Point3D speed) {
        this.speed = speed;
    }
}

