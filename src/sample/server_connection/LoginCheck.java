package sample.server_connection;


import sample.model.Database;
import sample.model.IDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LoginCheck extends Thread {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private IDatabase database;

    public LoginCheck(IDatabase db, Socket soc, DataInputStream sin, DataOutputStream sout) {
        database = db;
        socket = soc;
        in = sin;
        out = sout;
    }

    public void run() {
        try {
            String userName = in.readUTF();
            String password = in.readUTF();
            String answer = database.CheckPassword(userName, password);
            out.writeUTF(answer);
            answer = in.readUTF();
            if (answer.equals("new"))
                database.InsertNewUser(userName, password);
            socket.close();
            Thread.currentThread().stop();
        } catch (IOException e) { e.printStackTrace(); }
    }

}
