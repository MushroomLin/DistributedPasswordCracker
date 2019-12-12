package passwordcracker;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

class WorkerHandler extends Thread {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private JobUtils jobUtils;

    public WorkerHandler(ServerSocket server , JobUtils jobUtils) throws IOException {
        this.socket = server.accept();
        this.jobUtils = jobUtils;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            dataOutputStream.writeUTF(Server.hash);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String input;
        long last = 0;
        while (true) {
            try {
                input = dataInputStream.readUTF();
                String[] splited = input.split(",");
                String splitedOne = splited[0];
                if (splitedOne.equals("FOUND")) {
                    System.out.println("Success! Password = " + splited[1]);
                    Server.serverDataOutputStream.writeUTF(splited[1]);
                    Server.stop();
                } else if (splitedOne.equals("REQUEST_RANGE")) {
                    long cur = jobUtils.getNext();
                    Map<Long, Long> hashMap = JobUtils.hashMap;
                    if(hashMap.containsKey(last))
                        hashMap.remove(last);
                    last = cur;
                    long maxed = 0;
                    if(cur - jobUtils.jobLength > 0) {
                        maxed = cur - jobUtils.jobLength;
                    }
                    String result = Long.toString(maxed) + ',' + cur + ',' + jobUtils.pwdLength;
                    dataOutputStream.writeUTF(result);
                }
            } catch (SocketException | EOFException e) {
                try {
                    socket.close();
                    dataOutputStream.close();
                    dataInputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
