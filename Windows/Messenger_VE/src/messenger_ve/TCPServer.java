/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package messenger_ve;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author Gabriel
 */
public class TCPServer extends Thread {
    public static final int SERVERPORT = 8102;
    private boolean running = false;
    private PrintWriter mOut;
    private OnMessageReceived messageListener;
 
    public static void main(String[] args) {
 
        //Abre a janela de exibição (camada de apresentação para o usuário)
        ServerBoard frame = new ServerBoard();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public TCPServer(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }
    
    public void sendMessage(String message){
        if (mOut != null && !mOut.checkError()) {
            mOut.println(message);
            mOut.flush();
        }
    }
    
    @Override
    public void run() {
        super.run();
 
        running = true;
 
        try {
            System.out.println("S: Conectando...");
 
            //Criação do socket server
            ServerSocket serverSocket = new ServerSocket(SERVERPORT);
 
            //Cliente - aceitando conexão
            Socket client = serverSocket.accept();
            System.out.println("S: Recebendo...");
 
            try {
 
                //Envia para o cliente
                mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
 
                //Lê a mensagem do cliente
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
 
                //loop infinito para receber mensagens do cliente
                while (running) {
                    String message = in.readLine();
 
                    if (message != null && messageListener != null) {
                        //Chama o metodo de recebimento ao receber a mensagem
                        messageListener.messageReceived(message);
                    }
                }
 
            } catch (Exception e) {
                System.out.println("S: Erro");
                e.printStackTrace();
            } finally {
                client.close();
                System.out.println("S: Concluído!");
            }
 
        } catch (Exception e) {
            System.out.println("S: Erro");
            e.printStackTrace();
        }
 
    }
 
    
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
 
}
