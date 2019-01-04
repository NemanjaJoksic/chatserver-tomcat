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
public abstract class Message {

    private final ChatMessage chatMessage;

    public Message(Message message) {
        this.chatMessage = message.getChatMessage();
    }

    public Message(ChatMessage message) {
        this.chatMessage = message;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }

}
