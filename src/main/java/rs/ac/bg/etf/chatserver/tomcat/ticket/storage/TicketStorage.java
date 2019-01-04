/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.ticket.storage;

/**
 *
 * @author joksin
 */
public interface TicketStorage {
    
    public void put(String id, String ticket);
    
    public String get(String id);
    
    public void remove(String id);
    
}
