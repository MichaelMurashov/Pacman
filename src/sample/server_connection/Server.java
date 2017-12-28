package sample.server_connection;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.model.Database;
import sample.model.IDatabase;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server extends Application {

    public static ArrayList<User> users;
    public static ServerSocket ss;
    public static IDatabase database;

    @Override
    public void start(Stage primaryStage) throws Exception {
        database = new Database();
        database.Connect();

        int port = 6666;

        users = new ArrayList<>();

        try {
            ss = new ServerSocket(port);
            while (true){
                Socket socket = ss.accept();

                InputStream sin = socket.getInputStream();
                OutputStream sout = socket.getOutputStream();
                DataInputStream in = new DataInputStream(sin);
                DataOutputStream out = new DataOutputStream(sout);

                String input = in.readUTF();

                if (input.equals("create")) {
                    String name = in.readUTF();
                    users.add(new User(socket, name));
                }

                if (input.equals("find")) {
                    String name = in.readUTF();
                    users.add(new User(socket, name));
                }

                if (input.equals("check")){
                    LoginCheck checkThread = new LoginCheck(database, socket, in, out);
                    checkThread.setDaemon(true);
                    checkThread.start();
                }

                // когда набрались клиенты, начинаем игру
                if (users.size() == 2) {
                    for (User user : users)
                        user.WriteOut("start game");

                    for (User user : users) {
                        user.WriteOut(users.get(0).getName());
                        user.WriteOut(users.get(1).getName());
                    }

                    int num = 0;
                    for (User user: users) {
                        PlayerListener listener = new PlayerListener(user, num, users);
                        num++;
                        listener.setDaemon(true);
                        listener.start();
                    }
                }
            }
        } catch(Exception x) { x.printStackTrace(); }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
