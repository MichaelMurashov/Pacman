package sample.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.controller.GameThread;
import sample.controller.Map;

public class DrawMap {

    private Image emptyPic = new Image("empty.png");
    private Image pointPic = new Image("point.png");
    private Image wallPic = new Image("wall.png");

    public void draw(GraphicsContext gc, Map map) {
        for(int i = 0; i < GameThread.HEIGHT; i++)
            for(int j = 0; j < GameThread.WIDTH; j++) {
                if (map.GetMap(i,j)==map.EMPTY)
                    gc.drawImage(emptyPic, j*GameThread.CELL_SIZE, i*GameThread.CELL_SIZE);

                if (map.GetMap(i,j)==map.POINT)
                    gc.drawImage(pointPic, j*GameThread.CELL_SIZE, i*GameThread.CELL_SIZE);

                if (map.GetMap(i,j)==map.WALL)
                    gc.drawImage(wallPic, j*GameThread.CELL_SIZE, i*GameThread.CELL_SIZE);
            }
    }

}
