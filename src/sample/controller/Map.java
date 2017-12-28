package sample.controller;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.view.DrawMap;

import java.io.*;

public class Map {

    public static final int EMPTY = 0;
    public static final int POINT = 2;
    public static final int WALL = 1;

    private int array[][];

    private DrawMap drawMap;

    public void SetMap(int i, int j, int pointType) {
        array[i][j] = pointType;
    }
    public int GetMap(int i, int j) {
        return array[i][j];
    }

    public Map(){
        array=new int[GameThread.HEIGHT][GameThread.WIDTH];
        drawMap = new DrawMap();

        try {
            getMasFromFile("map.txt",array);
        }
        catch (IOException e){ System.out.print(e.getMessage()); }
    }

    private void getMasFromFile(String fileName, int mas[][]) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        for(int i = 0; i < GameThread.HEIGHT; i++) {
            for (int j = 0; j < GameThread.WIDTH; j++) {
                mas[i][j] = reader.read() - 48;
                System.out.print(mas[i][j]);
            }
            reader.read();
            reader.read();
            System.out.print("\n");
        }
        reader.close();
    }


    public void paint(GraphicsContext gc) {
        drawMap.draw(gc, this);
    }

    public int startPosI(int num) {
        int result = 0;
        if ((num==0)||(num==1)) result = 1;
        if ((num==2)||(num==3)) result = 9;
        return result;
    }

    public int startPosJ(int num) {
        int result = 0;
        if ((num==0)||(num==3)) result = 1;
        if ((num==1)||(num==2)) result = 13;
        return result;
    }

    public int startDirX(int num) {
        int result = 0;
        if (num==0) result = 1;
        if ((num==1)||(num==3)) result = 0;
        if (num==2) result = -1;
        return result;
    }

    public int startDirY(int num) {
        int result = 0;
        if (num==1) result = 1;
        if ((num==0)||(num==2)) result = 0;
        if (num==3) result = -1;
        return result;
    }
}
