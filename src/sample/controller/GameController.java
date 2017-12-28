package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import sample.server_connection.Client;
import sample.server_connection.ServerWriteRead;

import java.io.IOException;


public class GameController {

    @FXML
    public Canvas canvas;
    private static GraphicsContext graphics;

    private static Pacman[] pacmans;
    private int pacmansNum;

    private Pacman myPacman;
    private int myPacmanNum;
    private static String myName = "player1";

    private Map map;

    private ServerWriteRead serverWriteRead;

    public static GraphicsContext GetGraphics() {
        return graphics;
    }

    public static Pacman[] GetPacmans() {
        return pacmans;
    }

    public static String GetMyName() {
        return myName;
    }
    public static void SetMyName(String name) {
        myName = name;
    }

    @FXML
    public void initialize() {
        graphics = canvas.getGraphicsContext2D();
        map = new Map();
        pacmansNum = 2;

        serverWriteRead = new ServerWriteRead();

        try {
            pacmans = new Pacman[pacmansNum];

            for (int i = 0; i < pacmansNum; i++) {
                String name = serverWriteRead.readUTF();

                pacmans[i] = new Pacman(map.startPosI(i), map.startPosJ(i), map.startDirX(i), map.startDirY(i), name,
                        "pacman" + Integer.toString(i) + ".png");
                if (pacmans[i].getPlayerName().equals(myName)) {
                    myPacman = pacmans[i];
                    myPacmanNum = i;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread serverListener = new Thread() {
            @Override
            public void run() {
                serverListenerRun();
            }
        };
        serverListener.setDaemon(true);
        serverListener.start();

        GameThread gameThread = new GameThread(canvas, map);
        gameThread.setDaemon(true);
        gameThread.start();
    }

    public void keyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.LEFT) {
            serverWriteRead.writeUTF("left");

            myPacman.SetNewDirectionX(-1);
            myPacman.SetNewDirectionY(0);
        }

        if (keyEvent.getCode() == KeyCode.RIGHT) {
            serverWriteRead.writeUTF("right");

            myPacman.SetNewDirectionX(1);
            myPacman.SetNewDirectionY(0);
        }

        if (keyEvent.getCode() == KeyCode.UP) {
            serverWriteRead.writeUTF("up");

            myPacman.SetNewDirectionX(0);
            myPacman.SetNewDirectionY(-1);
        }

        if (keyEvent.getCode() == KeyCode.DOWN) {
            serverWriteRead.writeUTF("down");

            myPacman.SetNewDirectionX(0);
            myPacman.SetNewDirectionY(1);
        }
    }

    private void serverListenerRun() {
        int num = 0;
        String input = "";

        while (true) {
            try {
                num = serverWriteRead.readInt();
                input = serverWriteRead.readUTF();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (pacmans[num] != myPacman)
                switch (input) {
                    case "left":
                        pacmans[num].SetNewDirectionX(-1);
                        pacmans[num].SetNewDirectionY(0);
                        break;
                    case "right":
                        pacmans[num].SetNewDirectionX(1);
                        pacmans[num].SetNewDirectionY(0);
                        break;
                    case "up":
                        pacmans[num].SetNewDirectionX(0);
                        pacmans[num].SetNewDirectionY(-1);
                        break;
                    case "down":
                        pacmans[num].SetNewDirectionX(0);
                        pacmans[num].SetNewDirectionY(1);
                        break;
                }
        }
    }
}
