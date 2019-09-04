/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.service;

import serverchat.model.Message;
import serverchat.model.Response;
import serverchat.repository.MessageRepository;

/**
 *
 * @author NMQ
 */ 
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService() {
        messageRepository = new MessageRepository();
    }

    public Response sendMessage(Message message) {
        Response response = new Response();
        if (message != null) {
            if(message.getFromUser().getId()!=null && 
                    message.getToUser().getId()!=null &&
                    message.getContent()!=null){
                messageRepository.sendMessage(message);
            }
        }
        return response;
    }
}
