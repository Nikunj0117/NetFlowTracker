package com.example.sniffer;

public class PacketInfo {
    private String srcIp;
    private String destIp;
    private int srcPort;
    private int destPort;
    private String protocol;
    private int length;

    public PacketInfo(String srcIp, String destIp, int srcPort, int destPort, String protocol, int length) {
        this.srcIp = srcIp;
        this.destIp = destIp;
        this.srcPort = srcPort;
        this.destPort = destPort;
        this.protocol = protocol;
        this.length = length;
    }

    public String getSrcIp() { return srcIp; }
    public String getDestIp() { return destIp; }
    public int getSrcPort() { return srcPort; }
    public int getDestPort() { return destPort; }
    public String getProtocol() { return protocol; }
    public int getLength() { return length; }
}