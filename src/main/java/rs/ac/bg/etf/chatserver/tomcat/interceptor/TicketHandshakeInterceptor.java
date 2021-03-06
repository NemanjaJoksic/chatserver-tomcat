/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.interceptor;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.web.util.UriTemplate;
import rs.ac.bg.etf.chatserver.tomcat.ticket.auth.TicketAuthenticator;
import static rs.ac.bg.etf.chatserver.tomcat.config.ChatServerConfigurer.CHAT_URL;

/**
 *
 * @author joksin
 */
public class TicketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    public static final String USERNAME = "username";
    public static final String TICKET = "ticket";
    
    private static final Logger logger = LoggerFactory.getLogger(TicketHandshakeInterceptor.class);
    
    @Autowired
    private TicketAuthenticator ticketAuthenticator;
    
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        UriTemplate uriTemplate = new UriTemplate(CHAT_URL);
        Map<String, String> parameters = uriTemplate.match(request.getURI().toString());
        
        String username = parameters.get(USERNAME);
        String ticket = request.getHeaders().getFirst(TICKET);
        
        if(!ticketAuthenticator.validateTicket(username, ticket)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.flush();
            return false;
        }
        
        attributes.put(USERNAME, username);
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
    
}
