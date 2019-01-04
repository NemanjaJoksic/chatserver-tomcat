/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.actor.message;

import rs.ac.bg.etf.chatserver.tomcat.model.ChatMessage;

/**
 *
 * @author joksin
 */
public class MessageToClient extends Message {
    
    public MessageToClient(Message message) {
        super(message);
    }
    
    public MessageToClient(ChatMessage message) {
        super(message);
    }
    
}
