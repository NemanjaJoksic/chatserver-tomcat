/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.model;

/**
 *
 * @author joksin
 */
public class TicketDetails {
    
    private String ticket;
    private String username;
    private long timestamp;
    
    public TicketDetails() {
        
    }

    public TicketDetails(String ticket, String username, long timestamp) {
        this.ticket = ticket;
        this.username = username;
        this.timestamp = timestamp;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
}
