package rs.ac.bg.etf.chatserver.tomcat;

import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import rs.ac.bg.etf.chatserver.tomcat.config.Config;

@SpringBootApplication
@EnableWebSocket
public class Application {

    @Autowired
    private Config config;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public ActorSystem actorSystem() throws UnknownHostException {
        String localhostIpAddress = InetAddress.getLocalHost().getHostAddress();
        
        com.typesafe.config.Config akkaConfig = ConfigFactory.parseString(
                "akka.remote.netty.tcp.hostname=" + localhostIpAddress + "\n" +
                "akka.remote.netty.tcp.port=" + config.getAkkaNettyTcpPort())
                .withFallback(ConfigFactory.load());

        return ActorSystem.create(config.getAkkaSystemName(), akkaConfig);
    }

}
