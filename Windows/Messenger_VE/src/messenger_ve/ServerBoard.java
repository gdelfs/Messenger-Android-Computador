/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messenger_ve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Gabriel
 */
public class ServerBoard extends JFrame{
    private JTextArea messagesArea;
    private JButton sendButton;
    private JTextField message;
    private JButton startServer;
    private TCPServer mServer;

    public ServerBoard() {

        super("MSN do Delfs");

        JPanel panelFields = new JPanel();
        panelFields.setLayout(new BoxLayout(panelFields,BoxLayout.X_AXIS));

        JPanel panelFields2 = new JPanel();
        panelFields2.setLayout(new BoxLayout(panelFields2,BoxLayout.X_AXIS));

        //Tela das mensagens
        messagesArea = new JTextArea();
        messagesArea.setColumns(30);
        messagesArea.setRows(10);
        messagesArea.setEditable(false);

        sendButton = new JButton("Enviar");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recenbedo a mensagem : getText
                String messageText = message.getText();
                // Colocando a mensagem recebida em exibição
                messagesArea.append("\nVocê: " + messageText);
                // Enviando mensagem para o cliente (send message)
                mServer.sendMessage(messageText);
                //Limpando o campo
                message.setText("");
            }
        });

        startServer = new JButton("Iniciar conexão.");
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Após iniciar, desabilita o botão (conexao terá sido iniciada)
                startServer.setEnabled(false);

                //cria o objeto OnMessageReceived (ligado ao construtor do TCPServer)
                mServer = new TCPServer(new TCPServer.OnMessageReceived() {
                    @Override
                    public void messageReceived(String message) {
                        messagesArea.append("\n "+message);
                    }
                });
                mServer.start();

            }
        });

        //Campo de digitação
        message = new JTextField();
        message.setSize(200, 20);

        //adicionando os campos e botões
        panelFields.add(messagesArea);
        panelFields.add(startServer);

        panelFields2.add(message);
        panelFields2.add(sendButton);

        getContentPane().add(panelFields);
        getContentPane().add(panelFields2);

        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        setSize(300, 170);
        setVisible(true);
    }
}
