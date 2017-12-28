package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import sample.server_connection.Client;
import sample.server_connection.ServerWriteRead;

import java.io.IOException;

public class MenuController {
    public javafx.scene.control.TextField nameField;
    public javafx.scene.control.TextField passwordField;
    public Text textLabel;
    public Text creategame;
    public Text findgame;
    public Text exit;
    public Text wait;

    @FXML
    private AnchorPane pane;
    private String password;

    private ServerWriteRead serverWriteRead = new ServerWriteRead();

    public void exitgame(MouseEvent mouseEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void creategame(MouseEvent mouseEvent) throws IOException {
        serverWriteRead.connectToServer();
        serverWriteRead.writeUTF("create");
        serverWriteRead.writeUTF(GameController.GetMyName());

        String msg = serverWriteRead.readUTF();

        Stage stage = (Stage) pane.getScene().getWindow();
        Parent root =  FXMLLoader.load(getClass().getResource("../view/fxmls/game.fxml"));
        stage.setScene(new Scene( root, GameThread.CELL_SIZE* GameThread.WIDTH, GameThread.CELL_SIZE* GameThread.HEIGHT));
        stage.show();
    }

    public void findgame(MouseEvent mouseEvent) throws IOException {
        serverWriteRead.connectToServer();
        try {
            serverWriteRead.writeUTF("find");
            serverWriteRead.writeUTF(GameController.GetMyName());

            String msg = serverWriteRead.readUTF();

            Stage stage = (Stage) pane.getScene().getWindow();
            Parent gameRoot =  FXMLLoader.load(getClass().getResource("../view/fxmls/game.fxml"));
            stage.setScene(new Scene( gameRoot, GameThread.CELL_SIZE* GameThread.WIDTH, GameThread.CELL_SIZE* GameThread.HEIGHT));
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void menu(MouseEvent mouseEvent) throws IOException {
        GameController.SetMyName(nameField.getText());
        password = passwordField.getText();

        serverWriteRead.connectToServer();

        serverWriteRead.writeUTF("check");
        serverWriteRead.writeUTF(GameController.GetMyName());
        serverWriteRead.writeUTF(password);

        String answer = serverWriteRead.readUTF();

        if (answer.equals("incorrect")) {
            serverWriteRead.writeUTF("close");
            serverWriteRead.closeConnection();

            textLabel.setText("incorrect password");
        }
        else
            if (answer.equals("ok")) {
                serverWriteRead.writeUTF("close");
                serverWriteRead.closeConnection();

                ShowScene("../view/fxmls/menu.fxml");
            }
        else
                ShowScene("../view/fxmls/newUser.fxml");
    }

    private void ShowScene(String name) {
        Stage stage = (Stage) pane.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(name));
            stage.setScene(new Scene(root,600,400));
        } catch (IOException e) { e.printStackTrace(); }
        stage.show();
    }

    public void backToEnterName(MouseEvent mouseEvent) throws IOException {
        serverWriteRead.writeUTF("close");
        serverWriteRead.closeConnection();

        ShowScene("../view/fxmls/nameEnter.fxml");
    }

    public void createNewUser(MouseEvent mouseEvent) throws IOException {
        serverWriteRead.writeUTF("close");
        serverWriteRead.closeConnection();

        ShowScene("../view/fxmls/menu.fxml");
    }
}
