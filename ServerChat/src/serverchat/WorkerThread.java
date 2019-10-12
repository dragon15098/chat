/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

/**
 *
 * @author NMQ
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverchat.model.Message;
import serverchat.model.Request;

public class WorkerThread extends Thread {

    private Socket socket;

    public WorkerThread(Socket socket) {
        this.socket = socket;
    }

    public void s(){}
    
    public void run() {
        System.out.println("Processing: " + socket);
        try {
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
            ObjectInputStream objectInputStream = new ObjectInputStream(is);

            while (true) {
                try {
                    Request m = (Request) objectInputStream.readObject();
                    System.out.println(Thread.currentThread().getId());
                    System.out.println(m.getHeader());
//                    WorkerThread get = EchoChatMultiServer.list.get(0);
//                    get.s();
                } catch (ClassNotFoundException ex) {
                    
                    System.out.println("abc");
                    Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException e) {
            System.out.println("ab");
            System.err.println("Request Processing Error: " + e);
        }
        System.out.println("Complete processing: " + socket);
    }
}
