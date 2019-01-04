/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.actor;

import akka.actor.AbstractActor;
import rs.ac.bg.etf.chatserver.tomcat.actor.message.MessageFromClient;
import rs.ac.bg.etf.chatserver.tomcat.actor.message.MessageToClient;

/**
 *
 * @author joksin
 */
public abstract class ConnectionHolderActor extends AbstractActor {
    
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MessageToClient.class, this::messageToClient)
                .match(MessageFromClient.class, this::messageFromClient)
                .build();
    }
    
    protected abstract void messageFromClient(MessageFromClient message);
    protected abstract void messageToClient(MessageToClient message);
    
}
