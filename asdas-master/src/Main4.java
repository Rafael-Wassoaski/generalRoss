import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Main4 {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// Considerar que o número de generais falando a verdade deve ser 3f+1 para executar o algoritmo;
		ArrayList<Contato> contatosNG = new ArrayList<Contato>();
		ServerSocket serversocket;
		ServerSocket askingsocket;
		Socket conexao;
		Socket asker;
		Socket answer;
		Socket s1, s2;
		PrintWriter askpw;
		String resposta[] = null;
		int attacc = 0;
		int protecc = 0;
		int total = 0;
		String message[] = null;
		Boolean quemFala = false;
		
		Contato c2 = new Contato("localhost", 6782);
		Contato c0 = new Contato("localhost", 6781);
		Contato c3 = new Contato("localhost", 6783);
		
		contatosNG.add(c2);
		contatosNG.add(c0);
		contatosNG.add(c3);
		
		System.out.println("Iniciou");
			// Receber uma mensagem do general comandante e verificar com os outros contatos?
			while(message == null) {
				serversocket = new ServerSocket(6784); // Qual porta eu uso e como decido qual porta?
				conexao = serversocket.accept();
				System.out.println("Nova conexão com o cliente " + conexao.getInetAddress().getHostAddress());
				
				try {
					Scanner scn = new Scanner(conexao.getInputStream());
					message = scn.nextLine().split(";");
					System.out.println(message[0]);
					quemFala = Boolean.valueOf(message[1]);
					Thread.currentThread().sleep(5000);
				}catch(Exception e) {
					System.out.println("Não foi possível receber a mensagem");
				}
				//Será que esse while funciona? Não vejo como posso testar sozinho. PS: Agora eu consegui :D mas deu pau :c
				serversocket.close();
			}
			
			// A partir daqui eu verifico se a mensagem que eu recebi é a mesma das dos outros?
			// Criar uma lista de objetos Contato para cada general e fazer a verificação em cada um?
			Integer respostas = 0;
			
			
			askingsocket = new ServerSocket(6784);
			
		
			while(respostas < contatosNG.size()) {
				if(quemFala) {
						for(int i=0; i < contatosNG.size();) {
							asker = new Socket(contatosNG.get(i).getIp(),contatosNG.get(i).getPorta());
							askpw = new PrintWriter(asker.getOutputStream(), true);
							askpw.println(message[0] + ";" + false);
							System.out.println("Tentando: "+ contatosNG.get(i).getPorta());
							Scanner scn = new Scanner(asker.getInputStream());
							System.out.println("1");
							if(scn.hasNext()) {
								System.out.println("2");
							String respostaClient = scn.nextLine();
							System.out.println(respostaClient );
								if(respostaClient.equals("ok")) {
									System.out.println("3");
									i++;
									quemFala = false;
									
								}
							}
							asker.close();
						}
					}else {
				System.out.println("Aguardando respostas");
				answer = askingsocket.accept();
				System.out.println("Nova resposta do cliente " + answer.getInetAddress().getHostAddress());
				try {
					Scanner scn = new Scanner(answer.getInputStream());
					resposta = scn.nextLine().split(";");
					System.out.println(resposta[0]);
					askpw = new PrintWriter(answer.getOutputStream(), true);
					askpw.println("ok");
					if(resposta[0].equals("Atacar")) {
						attacc++;
					}else if(resposta[0].equals("Recuar")) {
						protecc++;
					}
				}catch(Exception e) {
					System.out.println("Não foi possível receber a mensagem");
				}
				respostas++; //Será que isso funfa? // Funfa
				System.out.println(respostas);
			}
		}
			
			
			
			System.out.println(attacc + ";" + protecc);
			// Se sim, como faço o algoritmo considerando a equação n >= 3f+1?
			// Faz isso antes
		
		
	}

}
