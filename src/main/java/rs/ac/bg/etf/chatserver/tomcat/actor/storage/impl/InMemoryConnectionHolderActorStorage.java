/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.actor.storage.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatserver.tomcat.actor.storage.ConnectionHolderActorStorage;

/**
 *
 * @author joksin
 */
@Component
public class InMemoryConnectionHolderActorStorage implements ConnectionHolderActorStorage {

    private final Map<String, String> map = new ConcurrentHashMap<>();
    
    @Override
    public void put(String id, String actor) {
        map.put(id, actor);
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
