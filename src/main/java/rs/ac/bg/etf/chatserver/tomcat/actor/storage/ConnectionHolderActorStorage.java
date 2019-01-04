/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.actor.storage;

/**
 *
 * @author joksin
 */
public interface ConnectionHolderActorStorage {
    
    public void put(String id, String actor);
    
    public String get(String id);
    
    public void remove(String id);
    
}
