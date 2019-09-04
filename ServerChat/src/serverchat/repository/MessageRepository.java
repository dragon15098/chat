/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.repository;

import serverchat.model.Message;
import serverchat.model.Response;

/**
 *
 * @author NMQ
 */
public class MessageRepository {
    
    private DBConnect connect;
    
    public MessageRepository() {
        
    }
    
    
    
    public Response sendMessage(Message message){
        String sql = "INSERT INTO MESSAGE VALUES()";
        return null;
    }
}
