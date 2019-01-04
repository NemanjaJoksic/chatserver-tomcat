/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.actor;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import rs.ac.bg.etf.chatserver.tomcat.actor.message.MessageFromClient;
import rs.ac.bg.etf.chatserver.tomcat.actor.message.MessageToClient;
import rs.ac.bg.etf.chatserver.tomcat.actor.storage.ConnectionHolderActorStorage;
import static rs.ac.bg.etf.chatserver.tomcat.interceptor.TicketHandshakeInterceptor.USERNAME;
import rs.ac.bg.etf.chatserver.tomcat.model.ChatMessage;

/**
 *
 * @author joksin
 */
public class SingleChatConnectionHolderActor extends ConnectionHolderActor {

    private static final Logger logger = LoggerFactory.getLogger(SingleChatConnectionHolderActor.class);
    
    private final ConnectionHolderActorStorage connectionHolderActorStorage;
    private final ActorSystem actorSystem;
    
    private final ExecutorService inputExecutorService = Executors.newSingleThreadExecutor();
    private final ExecutorService outputExecutorService = Executors.newSingleThreadExecutor();
    private final WebSocketSession session;
    private final ObjectMapper mapper;
    private final String username;
    
    public SingleChatConnectionHolderActor(WebSocketSession session, BeanFactory factory) {
        this.session = session;
        this.username = (String) session.getAttributes().get(USERNAME);
        this.connectionHolderActorStorage = factory.getBean(ConnectionHolderActorStorage.class);
        this.actorSystem = factory.getBean(ActorSystem.class);
        this.mapper = new ObjectMapper();
    }
    
    @Override
    protected void messageFromClient(MessageFromClient message) {
        inputExecutorService.submit(() -> {
            ChatMessage chatMessage = message.getChatMessage();
            chatMessage.setSender(username);
            
            String receiverAkkaPath = connectionHolderActorStorage.get(chatMessage.getReceiver());
            
            if(receiverAkkaPath == null) {
                logger.info("Receiver [{}] is offline. Storing message to DB.",
                        chatMessage.getReceiver());
            } else {
                ActorSelection actor = actorSystem.actorSelection(receiverAkkaPath);
                actor.tell(new  MessageToClient(chatMessage), getSelf());
                logger.info("[{}] Sending message to [{}]: {}", username, 
                        chatMessage.getReceiver(), chatMessage.getMessage());
            }
        });
    }

    @Override
    protected void messageToClient(MessageToClient message) {
        outputExecutorService.submit(() -> {
            try {
                ChatMessage chatMessage = message.getChatMessage();
                session.sendMessage(new TextMessage(mapper.writeValueAsString(chatMessage))); 
                logger.info("[{}] Message received from [{}]: {}", username,
                        chatMessage.getReceiver(), chatMessage.getMessage());
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        });
    }

}
