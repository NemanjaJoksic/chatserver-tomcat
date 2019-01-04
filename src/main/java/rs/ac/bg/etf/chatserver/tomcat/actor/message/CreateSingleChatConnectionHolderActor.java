/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.actor.message;

import akka.actor.Props;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.socket.WebSocketSession;
import rs.ac.bg.etf.chatserver.tomcat.actor.SingleChatConnectionHolderActor;
import static rs.ac.bg.etf.chatserver.tomcat.interceptor.TicketHandshakeInterceptor.USERNAME;

/**
 *
 * @author joksin
 */
public class CreateSingleChatConnectionHolderActor implements CreateConnectionHolderActor {

    private final WebSocketSession session;
    private final BeanFactory factory;

    public CreateSingleChatConnectionHolderActor(WebSocketSession session, BeanFactory factory) {
        this.session = session;
        this.factory = factory;
    }

    @Override
    public String id() {
        return (String) session.getAttributes().get(USERNAME);
    }
    
    @Override
    public Props props() {
        return Props.create(SingleChatConnectionHolderActor.class, session, factory);
    }

}
