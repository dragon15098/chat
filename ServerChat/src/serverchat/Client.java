/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

import serverchat.model.Request;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author NMQ
 */
public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("welcome client");
        Socket socket = new Socket("localhost", 4444);
        System.out.println("Client connected");
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Ok");
//        Message message = new Message();
//        message.setId(1);
        Request request = new Request();
        request.setHeader("getAllUser");
        os.writeObject(request);
        
//        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
//        Message returnMessage = (Message) is.readObject();
//        System.out.println("return Message is=" + returnMessage.getId());
        socket.close();
    }
}
