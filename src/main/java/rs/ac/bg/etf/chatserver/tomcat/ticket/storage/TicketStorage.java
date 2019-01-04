/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.ticket.storage;

import rs.ac.bg.etf.chatserver.tomcat.model.TicketDetails;

/**
 *
 * @author joksin
 */
public interface TicketStorage {
    
    public void put(String ticket, TicketDetails details);
    
    public TicketDetails get(String ticket);
    
    public void remove(String ticket);
    
}
