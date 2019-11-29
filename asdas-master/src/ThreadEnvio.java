import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ThreadEnvio implements Runnable {

	private ArrayList<Contato> contatosNG = new ArrayList<Contato>();
	private String msg;

	public ThreadEnvio(ArrayList<Contato> contatosNG, String msg) {
		super();
		this.contatosNG = contatosNG;
		this.msg = msg;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Socket asker;
			PrintWriter askpw;
			for (int i = 0; i < contatosNG.size();) {

				asker = new Socket(contatosNG.get(i).getIp(), contatosNG.get(i).getPorta());
				askpw = new PrintWriter(asker.getOutputStream(), true);
				askpw.println(msg);
				Scanner scn = new Scanner(asker.getInputStream());
				if (scn.hasNext()) {
					String respostaClient = scn.nextLine();
					System.out.println(respostaClient);
					if (respostaClient.equals("ok")) {
						i++;

					}
				}
				asker.close();
			}
			
			Thread.currentThread().stop();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
