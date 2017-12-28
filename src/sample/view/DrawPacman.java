package sample.view;

import javafx.scene.canvas.GraphicsContext;
import sample.controller.GameThread;
import sample.controller.Pacman;

public class DrawPacman {

    public void draw(GraphicsContext gc, Pacman pacman) {
        gc.drawImage(pacman.getPic(), pacman.GetX()-GameThread.CELL_SIZE/2, pacman.GetY()-GameThread.CELL_SIZE/2);
    }

}
