/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.ticket.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import rs.ac.bg.etf.chatserver.tomcat.ticket.auth.TicketAuthenticator;
import rs.ac.bg.etf.chatserver.tomcat.ticket.storage.TicketStorage;

/**
 *
 * @author joksin
 */
@Component
@ConditionalOnProperty(name = "app.ticket.authentication.type", havingValue = "uuid")
public class UUIDTicketAuthenticator implements TicketAuthenticator {

    @Autowired
    private TicketStorage ticketStorage;

    @Override
    public boolean validateTicket(String id, String ticket) {
        String validTicket = ticketStorage.get(id);
        boolean valid = validTicket != null && validTicket.equals(ticket);
        if(valid)
            ticketStorage.remove(id);
        return valid;
    }

}
