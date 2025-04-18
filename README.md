# Network Sniffer Web Interface

A modern, real-time network packet sniffer and flow visualizer built with Java Spring Boot and Pcap4J. Captured packet data (source/destination IPs, ports, protocols, lengths) is streamed via WebSockets to an interactive web dashboard using Bootstrap and DataTables.

---

## Key Features

- **Real-Time Capture**: Leverages Pcap4J to sniff live TCP/UDP traffic on your chosen network interface.
- **WebSocket Streaming**: Uses Spring Boot’s WebSocket (STOMP over SockJS) to push packet events to the browser.
- **Interactive Dashboard**: Responsive UI with Bootstrap 5 and DataTables for sorting, searching, and pagination.
- **Dynamic Interface Selection**: Configure which NIC to monitor via a simple index in `Sniffer.java`.
- **Debug Logging**: Console logs for captured packets and broadcast actions to aid troubleshooting.
- **Clean, Modern UI**: Gradient navbar, card layout, row animations, and clear controls (e.g., "Clear Table").

---

## Prerequisites

- Java 17 or above
- Maven 3.6+ (or use the included Maven Wrapper)
- Npcap (Windows) or libpcap (Linux/macOS) installed

---

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/network-sniffer-web.git
   cd network-sniffer-web
   ```

2. **Configure the network interface**
   Open `Sniffer.java`, locate:
   ```java
   int indexToUse = 4; // change to match your NIC index
   ```
   Re-run the app to print available interfaces, then set this value to your Wi‑Fi/Ethernet adapter’s index.

3. **Build & Run**
   - Using Maven:
     ```bash
     mvn clean package
     java -jar target/network-sniffer-web-1.0.0.jar
     ```
   - Or via IDE: run `Application.java` (annotated with `@SpringBootApplication`).

4. **Access the Dashboard**
   Open your browser at [http://localhost:8080](http://localhost:8080).

5. **Generate Traffic**
   Ping or browse to see live packet rows:
   ```bash
   ping google.com
   curl https://example.com
   ```

---

## Project Structure

```
network-sniffer-web/
├─ src/main/java/com/example/sniffer/
│  ├─ Application.java           # Spring Boot launcher
│  ├─ WebSocketConfig.java       # WebSocket/STOMP config
│  ├─ PacketBroadcaster.java     # Sends DTOs over WebSockets
│  ├─ PacketInfo.java            # DTO for packet data
│  └─ Sniffer.java               # Captures & broadcasts packets
└─ src/main/resources/static/
   └─ index.html                 # Interactive dashboard
```

---

## Customization

- **Table Columns**: Edit `index.html` to add or remove fields.
- **Styling**: Override Bootstrap variables or add custom CSS in `index.html`.
- **Flow Features**: Extend `PacketInfo` and `Sniffer` to include additional metadata.

---

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

