package passwordcracker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

@RestController
public class ApplicationController {
	private final static int PORT = 8123;
	private final static String SERVERIP = "127.0.0.1";
	@RequestMapping("/cracking")
	public String cracking(@RequestParam(value="hash", defaultValue="") String hash) {
		try {
			Server s = new Server();
			Thread t = new Thread(s);
			t.start();
			Thread.sleep(1000);
			Socket socket = new Socket(SERVERIP, PORT);
			DataInputStream userInput = new DataInputStream(socket.getInputStream());
			DataOutputStream userOutput = new DataOutputStream(socket.getOutputStream());
			userOutput.writeUTF(hash);
			String password = userInput.readUTF();

			System.out.println("Current Pwd: " + password);
			socket.close();
			return "Pwd: "+password;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Cracking Failed!";
	}
}
