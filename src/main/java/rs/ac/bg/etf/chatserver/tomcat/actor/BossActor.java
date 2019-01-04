/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import rs.ac.bg.etf.chatserver.tomcat.actor.message.CreateConnectionHolderActor;
import rs.ac.bg.etf.chatserver.tomcat.actor.message.DestroyConnectionHolderActor;
import rs.ac.bg.etf.chatserver.tomcat.actor.storage.ConnectionHolderActorStorage;
import rs.ac.bg.etf.chatserver.tomcat.config.Config;

/**
 *
 * @author joksin
 */
public class BossActor extends AbstractActor {
    
    private static final Logger logger = LoggerFactory.getLogger(BossActor.class);
    
    private final BeanFactory factory;
    private final Config config;
    private final ConnectionHolderActorStorage connectionHolderActorStorage;
    private final String localhostIpAddress;
    
    public BossActor(BeanFactory factory) throws UnknownHostException {
        this.factory = factory;
        this.config = factory.getBean(Config.class);
        this.connectionHolderActorStorage = factory.getBean(ConnectionHolderActorStorage.class);
        this.localhostIpAddress = InetAddress.getLocalHost().getHostAddress();
    }
    
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CreateConnectionHolderActor.class, this::createConnectionHolderActor)
                .match(DestroyConnectionHolderActor.class, this::destroyConnectionHolderActor)
                .build();
    }

    private void createConnectionHolderActor(CreateConnectionHolderActor message) {
        ActorRef actor = getContext().actorOf(message.props());
        getContext().watch(actor);
        String akkaActorRemoteAddress = actor.path().toString().replace("akka://" + config.getAkkaSystemName() + "/", "akka.tcp://" + config.getAkkaSystemName() + "@" + localhostIpAddress + ":" + config.getAkkaNettyTcpPort() + "/");
        connectionHolderActorStorage.put(message.id(), akkaActorRemoteAddress);
        logger.info("Actor created. Remote address={}", akkaActorRemoteAddress);
    }
    
    private void destroyConnectionHolderActor(DestroyConnectionHolderActor message) {
    }
    
}

