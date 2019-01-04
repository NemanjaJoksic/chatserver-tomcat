/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.ticket.storage.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import rs.ac.bg.etf.chatserver.tomcat.model.TicketDetails;
import rs.ac.bg.etf.chatserver.tomcat.ticket.storage.TicketStorage;

/**
 *
 * @author joksin
 */
@Repository
@ConditionalOnProperty(name = "app.ticket.storage.type", havingValue = "in-memory")
public class InMemoryTicketStorage implements TicketStorage {
    
    private final Map<String, TicketDetails> map = new ConcurrentHashMap<>();
    
    @PostConstruct
    public void initMap() {
        map.put("123", new TicketDetails("123", "nemanja", System.currentTimeMillis()));
        map.put("1234", new TicketDetails("1234", "marko", System.currentTimeMillis()));
    }
    
    @Override
    public void put(String ticket, TicketDetails details) {
        map.put(ticket, details);
    }
    
    @Override
    public TicketDetails get(String ticket) {
        return map.get(ticket);
    }

    @Override
    public void remove(String ticket) {
        map.remove(ticket);
    }
    
}
