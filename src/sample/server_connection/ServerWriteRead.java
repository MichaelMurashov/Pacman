package sample.server_connection;

import java.io.IOException;

public class ServerWriteRead {

    public void connectToServer() {
        Client.connectToServer();
    }

    public void writeUTF(String str) throws IOException {
        Client.out.writeUTF(str);
    }

    public String readUTF() throws  IOException {
        return Client.in.readUTF();
    }

    public int readInt() throws IOException {
        return Client.in.readInt();
    }

    public void closeConnection() throws  IOException {
        Client.socket.close();
    }

}
