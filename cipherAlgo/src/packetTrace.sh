#!/bin/bash

# Interface to capture packets (replace with your network interface)
INTERFACE="en0"

# Output file to store captured packets
OUTPUT_FILE="captured_packets.pcap"

# Start capturing packets using tcpdump
echo "Capturing packets on interface $INTERFACE..."
sudo tcpdump -i $INTERFACE -w $OUTPUT_FILE

# After capture is stopped, display message
echo "Packets captured and saved to $OUTPUT_FILE"

