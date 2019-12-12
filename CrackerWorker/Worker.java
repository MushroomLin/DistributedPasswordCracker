package passwordcracker;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

class Worker {
    private final static int PORT = 8123;
    private final static String HOST = "128.112.170.32";
    public static void main(String[] arg) {
        try {
            Cracker cracker = new Cracker();

            for (int i = 0; i < 52; ++i) {
                char ch = ' ';
                if(i < 26) {
                    ch = 'a';
                } else {
                    ch = 'A';
                }
                cracker.characters[i] = (char) (i + ch);
            }

            Socket socket = new Socket(HOST , PORT);
            System.out.println("Server connected!");

            DataInputStream clientInput = new DataInputStream(socket.getInputStream());
            DataOutputStream clientOutput = new DataOutputStream(socket.getOutputStream());
            String hash = clientInput.readUTF();
            System.out.println("hash: " + hash);

            while(true) {
                clientOutput.writeUTF("REQUEST_RANGE");

                String[] splited = clientInput.readUTF().split(",");
                long from = Long.parseLong(splited[0]);
                long to = Long.parseLong(splited[1]);
                int lengthT = Integer.parseInt(splited[2]);

                System.out.println("from: "  + from + ", to: " + to);

                String result = cracker.iterate(from, to, lengthT, hash);
                if(result != null) {
                    System.out.println("Cracking success!");
                    clientOutput.writeUTF("FOUND," + result);
                    break;
                }
            }
            socket.close();
            clientInput.close();
            clientOutput.close();
        } catch (SocketException | EOFException e) {
            System.out.println("Server cannot be connected!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}