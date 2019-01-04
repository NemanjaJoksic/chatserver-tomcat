/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.actor.message;

import akka.actor.Props;

/**
 *
 * @author joksin
 */
public interface CreateConnectionHolderActor {
    
    public String id();
    public Props props();
    
}
