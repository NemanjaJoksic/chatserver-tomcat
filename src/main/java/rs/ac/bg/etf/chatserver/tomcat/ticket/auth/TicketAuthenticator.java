/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.ticket.auth;

import rs.ac.bg.etf.chatserver.tomcat.model.TicketDetails;

/**
 *
 * @author joksin
 */
public interface TicketAuthenticator {
    
    public TicketDetails validateTicket(String ticket);
    
}
