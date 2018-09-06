package delfino.messenger_ve;

/**
 * Created by Gabriel on 21/05/2017.
 */

import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {
    private String serverMessage;
    public static final String SERVERIP = "192.168.43.73"; //IP do computador! Editar para conexões em outros computadores
    public static final int SERVERPORT = 8102;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;

    /**
     *  Construtor da classe (listener)
     */
    public TCPClient(OnMessageReceived listener) {
        mMessageListener = listener;
    }

    //Enviando mensagem do cliente para o servidor
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }

    //Fecha aplicação
    public void stopClient(){
        mRun = false;
    }

    public void run() {

        mRun = true;

        try {
            //Chama o IP address definido anteriormente (ip do computador que irá ser o servidor).
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);

            Log.e("TCP Client", "C: Conectando...");

            //Criação do socket para fazer conexão com o servidor
            Socket socket = new Socket(serverAddr, SERVERPORT);

            try {

                //Enviando mensagem para o servidor
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                Log.e("TCP Client", "C: Enviado.");

                Log.e("TCP Client", "C: Concluído!");

                //Recebimento de mensagem do Servidor
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //Enquanto o cliente busca por mensagens enviadas pelo servidor
                while (mRun) {
                    serverMessage = in.readLine();

                    if (serverMessage != null && mMessageListener != null) {
                        //Chama o metodo de recebimento de mensagem
                        mMessageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;

                }

                Log.e("RESPONSE FROM SERVER", "S: Mensagem recebida: '" + serverMessage + "'");

            } catch (Exception e) {

                Log.e("TCP", "S: Erro", e);

            } finally {
                //Socket fechado.
                // Será necessário criar um novo socket.
                socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }


    }


    public interface OnMessageReceived {
        public void messageReceived(String message);
    }


}
