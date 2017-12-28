package sample.server_connection;

import java.io.IOException;
import java.util.ArrayList;

public class PlayerListener extends Thread {

    public ArrayList<User> users;
    private User player;
    private int num;

    public PlayerListener(User player_, int num_, ArrayList<User> users_) {
        users = users_;
        player = player_;
        num = num_;
    }

    public void run() {
        try {
            String input = "";
            while (true) {
                input = player.ReadIn();
                for (User user: users) {
                    user.WriteOutInt(num);
                    user.WriteOut(input);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

}
