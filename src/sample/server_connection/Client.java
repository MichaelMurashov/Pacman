package sample.server_connection;

import java.net.*;
import java.io.*;

public class Client {

    public static DataInputStream in;
    public static DataOutputStream out;
    public static Socket socket;

    public static void connectToServer() {
        try {
            int serverPort = 6666;
            String address = "127.0.0.1";
            InetAddress ipAddress = InetAddress.getByName(address);

            socket = new Socket(ipAddress, serverPort);

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);
        } catch (Exception x) { x.printStackTrace(); }
    }
}
