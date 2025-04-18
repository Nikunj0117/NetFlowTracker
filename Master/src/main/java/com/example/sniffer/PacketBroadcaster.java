package com.example.sniffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class PacketBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public PacketBroadcaster(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcast(PacketInfo packet) {
        messagingTemplate.convertAndSend("/topic/packets", packet);
    }
}
