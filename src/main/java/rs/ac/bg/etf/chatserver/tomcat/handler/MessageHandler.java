/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.handler;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import rs.ac.bg.etf.chatserver.tomcat.actor.BossActor;
import rs.ac.bg.etf.chatserver.tomcat.actor.message.CreateSingleChatConnectionHolderActor;
import rs.ac.bg.etf.chatserver.tomcat.actor.message.MessageFromClient;
import rs.ac.bg.etf.chatserver.tomcat.actor.storage.ConnectionHolderActorStorage;
import static rs.ac.bg.etf.chatserver.tomcat.interceptor.TicketHandshakeInterceptor.TICKET;
import static rs.ac.bg.etf.chatserver.tomcat.interceptor.TicketHandshakeInterceptor.USERNAME;
import rs.ac.bg.etf.chatserver.tomcat.model.ChatMessage;

/**
 *
 * @author joksin
 */
public class MessageHandler extends TextWebSocketHandler { 
    
    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    private ActorSystem actorSystem;
    
    @Autowired
    private BeanFactory factory;
    
    @Autowired
    private ConnectionHolderActorStorage connectionHolderActorStorage;
    
    private ActorRef bossActor;
    
    @PostConstruct
    public void init() {
        bossActor = actorSystem.actorOf(Props.create(BossActor.class, factory));
    }
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String ticket = session.getHandshakeHeaders().getFirst(TICKET);
        String username = (String) session.getAttributes().get(USERNAME);
        
        bossActor.tell(new CreateSingleChatConnectionHolderActor(session, factory), ActorRef.noSender());
        
        logger.info("[Session:{}] Connection established [ticket:{}] [{}]", 
                session.getId(), ticket, username);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get(USERNAME);
        logger.info("[Session:{}] Connection closed [{}]", session.getId(), username);
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage chatMessage = mapper.readValue(message.getPayload(), ChatMessage.class);
        
        String username = (String) session.getAttributes().get(USERNAME);
        ActorSelection actor = actorSystem.actorSelection(connectionHolderActorStorage.get(username));
        actor.tell(new  MessageFromClient(chatMessage), ActorRef.noSender());
        
//        logger.info("[Session:{}] receiver: {}, message: \"{}\"", session.getId(), 
//                chatMessage.getReceiver(), chatMessage.getMessage());
//        session.sendMessage(new TextMessage("Message \"" + message.getPayload() + "\" has been received"));
    }
    
}
