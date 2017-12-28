package sample.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import sample.view.DrawMap;

public class GameThread extends Thread {

    public static final int CELL_SIZE = 31;
    public static final int WIDTH = 15;
    public static final int HEIGHT = 11;

    private Canvas canvas;
    private Map map;

    public GameThread(Canvas canv, Map map_) {
        canvas = canv;
        map = map_;
    }

    public void run() {
        while (true) {
            Update();
            Draw();
        }
    }

    private void Draw() {
        GameController.GetGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawFrame(GameController.GetGraphics());
    }

    private void Update() {
        calculateScene();
        try {
            Thread.sleep(28);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void drawFrame(GraphicsContext gc) {
        map.paint(gc);
        for (Pacman pacman : GameController.GetPacmans()) {
            pacman.paint(gc);
        }
    }

    private void calculateScene() {
        for (Pacman pacman : GameController.GetPacmans()) {
            pacman.move(map);
        }
    }


}
