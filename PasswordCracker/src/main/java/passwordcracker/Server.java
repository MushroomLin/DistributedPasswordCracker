package passwordcracker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server implements Runnable {
    private static final int LENGTH = 52;
    private static final int PORT = 8123;
    private static final int PWD_LENGTH = 5;
    private static final int JOB_LENGTH = 5000000;
    private static long start;
    public static String hash;
    public static Socket mySocket;
    public static DataInputStream serverDataInputStream;
    public static DataOutputStream serverDataOutputStream;

    public static void stop() throws IOException {
        mySocket.close();
        serverDataInputStream.close();
        serverDataOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("Cracking end: Total time "+(end-start));
        System.exit(0);
    }

    @Override
    public void run() {
        try {
            JobUtils jobUtils = new JobUtils(LENGTH, PWD_LENGTH, JOB_LENGTH);
            ServerSocket server = new ServerSocket(PORT);

            mySocket = server.accept();

            serverDataInputStream = new DataInputStream(mySocket.getInputStream());
            serverDataOutputStream = new DataOutputStream(mySocket.getOutputStream());

            hash = serverDataInputStream.readUTF();
            start = System.currentTimeMillis();
            while (true) {
                System.out.println("Server listens: ");
                new WorkerHandler(server, jobUtils).start();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}