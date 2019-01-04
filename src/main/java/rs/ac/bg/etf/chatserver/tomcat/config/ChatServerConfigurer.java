/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import rs.ac.bg.etf.chatserver.tomcat.handler.MessageHandler;
import rs.ac.bg.etf.chatserver.tomcat.interceptor.TicketHandshakeInterceptor;

/**
 *
 * @author joksin
 */
@Configuration
public class ChatServerConfigurer implements WebSocketConfigurer {
    
    public static final String CHAT_URL = "/chat";
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler(), CHAT_URL).addInterceptors(ticketHandshakeInterceptor());
    }

    @Bean
    public WebSocketHandler messageHandler() {
        return new MessageHandler();
    }
    
    @Bean
    public TicketHandshakeInterceptor ticketHandshakeInterceptor() {
        return new TicketHandshakeInterceptor();
    }

}
