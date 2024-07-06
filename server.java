import java.net.*;
import java.io.*;

class server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    // Constructor
    public server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is ready to connection...");
            System.out.println("Waiting...");

            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("Reader started...");
            while (true) {
                try {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat");
                        System.out.println("Goes offline");
                        socket.close();
                        break;
                    }
                    System.out.println("Sachin: " + msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("Writing started...");
            while (true) {
                try {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is the server page...");
        new server();
    }
}
