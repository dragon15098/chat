/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

import serverchat.repository.DBConnect;
import serverchat.model.Request;
import serverchat.model.AppUser;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverchat.model.Message;
import serverchat.service.MessageService;

/**
 *
 * @author NMQ
 */
public class ServerChat {

    private static final int PORT = 4444;

    public static void main(String[] args) throws ClassNotFoundException {
        MessageService messageService = new MessageService();
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                Request request = (Request) inputStream.readObject();
                String content = request.getHeader();

//              to do list 
//              lấy danh sách người đã kết bạn 
//              lấy tin nhắn người đã kết bạn
//              lấy group 
//              tin nhắn group 
//              thêm bạn 
//              đồng ý kết bạn
//              danh sách lời nhắn kết bạn
//              nhắn tin tới group 
//              nhắn tin đơn
                switch (content) {
                    case "getAllUser":

                        break;
                    case "sendMessage":
                        messageService.sendMessage((Message) request.getContent());
                        break;
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
