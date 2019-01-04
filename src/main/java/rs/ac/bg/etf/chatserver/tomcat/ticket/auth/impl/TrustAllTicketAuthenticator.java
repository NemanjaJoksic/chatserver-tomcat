/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.ticket.auth.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatserver.tomcat.ticket.auth.TicketAuthenticator;

/**
 *
 * @author joksin
 */
@Component
@ConditionalOnProperty(name = "app.ticket.authentication.type", havingValue = "none")
public class TrustAllTicketAuthenticator implements TicketAuthenticator {

    @Override
    public boolean validateTicket(String id, String ticket) {
        return true;
    }
    
}
