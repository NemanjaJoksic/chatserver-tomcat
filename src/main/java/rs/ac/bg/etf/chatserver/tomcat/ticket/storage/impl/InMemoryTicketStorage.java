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
import rs.ac.bg.etf.chatserver.tomcat.ticket.storage.TicketStorage;

/**
 *
 * @author joksin
 */
@Repository
@ConditionalOnProperty(name = "app.ticket.storage.type", havingValue = "in-memory")
public class InMemoryTicketStorage implements TicketStorage {
    
    private final Map<String, String> map = new ConcurrentHashMap<>();
    
    @PostConstruct
    public void initMap() {
        map.put("nemanja", "123");
        map.put("marko", "123");
    }
    
    @Override
    public void put(String id, String ticket) {
        map.put(id, ticket);
    }
    
    @Override
    public String get(String id) {
        return map.get(id);
    }

    @Override
    public void remove(String id) {
        map.remove(id);
    }
    
}
