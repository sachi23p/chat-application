import java.net.Socket;
import java.io.*;

public class client {
    Socket socket;  
    BufferedReader br;
    PrintWriter out;

    public client() {
        try {
            System.out.println("Sending request to server...");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("Connection done...");

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
            try {
                while (!socket.isClosed()) {
                    String msg = br.readLine();
                    if (msg == null || msg.equals("exit")) {
                        System.out.println("Server terminated the chat");
                        System.out.println("Goes offline");
                        socket.close();
                        break;
                    }
                    System.out.println("Gaurav: " + msg);
                }
            } catch (Exception e) {
                System.out.println("Connection closed.");
                e.printStackTrace();
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("Writer started...");
            try {
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                while (!socket.isClosed()) {
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equalsIgnoreCase("exit")) {
                        socket.close();
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Connection closed.");
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is client page...");
        new client();
    }
}
