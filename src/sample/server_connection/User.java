package sample.server_connection;


import java.io.*;
import java.net.Socket;

public class User {

    private Socket socket;
    private String name;
    private DataInputStream in;
    private DataOutputStream out;

    public User(Socket soc, String nm) {
        socket = soc;
        name = nm;
        try {
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String ReadIn() throws IOException {
        return in.readUTF();
    }

    public void WriteOut(String message) {
        try {
            out.writeUTF(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void WriteOutInt(int message) {
        try {
            out.writeInt(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
