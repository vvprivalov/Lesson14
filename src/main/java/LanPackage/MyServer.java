package LanPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer {
    private final Scanner scanner;

    public static void main(String... args) {
        new MyServer();
    }

    public MyServer() {
        scanner = new Scanner(System.in);
        serverUse();
    }

    public void serverUse() {
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            System.out.println("Ждем подключения...");
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                while (true) {
                    String msg = scanner.nextLine();
                    try {
                        outputStream.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (msg.equals("quit")) {
                        break;
                    }
                }
            }).start();

            while (true) {
                String str = inputStream.readUTF();
                if (str.equals("quit")) {
                    outputStream.writeUTF(str);
                    System.out.println("Сервер завершил работу");
                    break;
                } else {
                    System.out.println(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

