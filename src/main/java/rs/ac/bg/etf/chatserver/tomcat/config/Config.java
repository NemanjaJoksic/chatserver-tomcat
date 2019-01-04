/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.etf.chatserver.tomcat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author joksin
 */
@Configuration
public class Config {
    
    @Value("${app.akka.remote.netty.tcp}")
    private Integer akkaNettyTcpPort;
    
    @Value("${app.akka.system.name}")
    private String akkaSystemName;

    public Integer getAkkaNettyTcpPort() {
        return akkaNettyTcpPort;
    }

    public void setAkkaNettyTcpPort(Integer akkaNettyTcpPort) {
        this.akkaNettyTcpPort = akkaNettyTcpPort;
    }

    public String getAkkaSystemName() {
        return akkaSystemName;
    }

    public void setAkkaSystemName(String akkaSystemName) {
        this.akkaSystemName = akkaSystemName;
    }
    
}
