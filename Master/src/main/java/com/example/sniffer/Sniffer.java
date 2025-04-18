package com.example.sniffer;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.IpV4Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Sniffer implements CommandLineRunner {

    private final PacketBroadcaster broadcaster;

    @Autowired
    public Sniffer(PacketBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @Override
    public void run(String... args) throws Exception {
        List<PcapNetworkInterface> devices = Pcaps.findAllDevs();
        if (devices.isEmpty()) {
            System.out.println("❌ No network interfaces found.");
            return;
        }

        System.out.println("Available Network Interfaces:");
        for (int i = 0; i < devices.size(); i++) {
            System.out.println(i + ": " + devices.get(i).getName() + " - " + devices.get(i).getDescription());
        }

        int indexToUse = 4; // <-- CHANGE THIS to your actual NIC index
        PcapNetworkInterface nif = devices.get(indexToUse);
        System.out.println("✅ Using interface: " + nif.getName() + " - " + nif.getDescription());

        PcapHandle handle = nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);

        PacketListener listener = packet -> {
            if (packet.contains(IpV4Packet.class)) {
                IpV4Packet ip = packet.get(IpV4Packet.class);
                String srcIp = ip.getHeader().getSrcAddr().getHostAddress();
                String destIp = ip.getHeader().getDstAddr().getHostAddress();
                String protocol = "OTHER";
                int srcPort = 0;
                int destPort = 0;

                if (packet.contains(TcpPacket.class)) {
                    TcpPacket tcp = packet.get(TcpPacket.class);
                    srcPort = tcp.getHeader().getSrcPort().valueAsInt();
                    destPort = tcp.getHeader().getDstPort().valueAsInt();
                    protocol = "TCP";
                } else if (packet.contains(UdpPacket.class)) {
                    UdpPacket udp = packet.get(UdpPacket.class);
                    srcPort = udp.getHeader().getSrcPort().valueAsInt();
                    destPort = udp.getHeader().getDstPort().valueAsInt();
                    protocol = "UDP";
                }

                System.out.println("🧪 Captured: " + srcIp + ":" + srcPort + " → " + destIp + ":" + destPort + " | " + protocol);

                PacketInfo info = new PacketInfo(srcIp, destIp, srcPort, destPort, protocol, packet.length());
                System.out.println("📢 Broadcasting packet to WebSocket");
                broadcaster.broadcast(info);
            }
        };

        System.out.println("Sniffing started...");
        handle.loop(-1, listener);
    }
}
