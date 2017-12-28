package sample.controller;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.view.DrawPacman;

public class Pacman {

    private int X,Y;

    private int directionX,directionY;
    private int newDirectionX,newDirectionY;
    private double speed;

    private String playerName;

    private Image pic;

    private DrawPacman drawPacman;

    public int GetX() {
        return X;
    }
    public int GetY() {
        return Y;
    }

    public int GetNewDirectionX() {
        return newDirectionX;
    }
    public int GetNewDirectionY() {
        return newDirectionY;
    }
    public void SetNewDirectionX(int x) {
        newDirectionX = x;
    }
    public void SetNewDirectionY(int y) {
        newDirectionY = y;
    }

    public Pacman(int i,int j, int dirX, int dirY, String name, String imageName){
        X = j* GameThread.CELL_SIZE + GameThread.CELL_SIZE/2;
        Y = i* GameThread.CELL_SIZE + GameThread.CELL_SIZE/2;

        newDirectionX = directionX = dirX;
        newDirectionY = directionY = dirY;

        speed = 1.0;

        playerName = name;
        pic = new Image(imageName);

        drawPacman = new DrawPacman();
    }

    public void move(Map map){
        if ( (X%GameThread.CELL_SIZE == GameThread.CELL_SIZE/2) && (Y%GameThread.CELL_SIZE == GameThread.CELL_SIZE/2) ){
            if (map.GetMap(Y/GameThread.CELL_SIZE, X/GameThread.CELL_SIZE) == map.POINT){
                map.SetMap(Y/GameThread.CELL_SIZE, X/GameThread.CELL_SIZE, map.EMPTY);
            }
            if (map.GetMap((Y+newDirectionY*GameThread.CELL_SIZE)/GameThread.CELL_SIZE, (X+newDirectionX*GameThread.CELL_SIZE)/GameThread.CELL_SIZE)!= map.WALL) {
                directionX = newDirectionX;
                directionY = newDirectionY;
                X += directionX * speed;
                Y += directionY * speed;
            }
            else if (map.GetMap((Y+directionY*GameThread.CELL_SIZE)/GameThread.CELL_SIZE, (X+directionX* GameThread.CELL_SIZE)/ GameThread.CELL_SIZE) != map.WALL) {
                X += directionX * speed;
                Y += directionY * speed;
            }
        }
        else{
            X += directionX * speed;
            Y += directionY * speed;
        }
    }

    public void paint(GraphicsContext gc) {
        drawPacman.draw(gc,this);
    }

    public String getPlayerName() {
        return playerName;
    }

    public Image getPic() {
        return pic;
    }
}
