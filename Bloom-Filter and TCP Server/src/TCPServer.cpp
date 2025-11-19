#include "TCPServer.h"
#include <iostream>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#include <thread>
#include "FooBar.h"

using namespace std;

void initBloomFilter(int sock) {
    const int server_port = 5555;
    // Set up the address structure
    struct sockaddr_in sin;
    // Clear the structure
    memset(&sin, 0, sizeof(sin));
    // Use IPv4
    sin.sin_family = AF_INET;
    // Allow any address to connect
    sin.sin_addr.s_addr = INADDR_ANY;
    // Set the port number
    sin.sin_port = htons(server_port);
    // Bind the socket to the address and port number
    if (bind(sock, (struct sockaddr *) &sin, sizeof(sin)) < 0) {
        perror("Error binding socket");
        return;
    }
    // Listen for incoming connections
    // The second argument is the maximum number of connections that can be queued
    if (listen(sock, 5) < 0) {
        perror("Error listening to a socket");
        return;
    }

    // Initialize the bloom filter
    // The address structure for the client
    struct sockaddr_in client_sin;
    // The length of the address structure
    unsigned int addr_len = sizeof(client_sin);
    if (listen(sock, 5) < 0) {
        perror("Error listening to a socket");
        return;
    }
    // Accept the connection
    int client_sock = accept(sock, (struct sockaddr *) &client_sin, &addr_len);
    if (client_sock < 0) {
        perror("Error accepting client");
        return;
    }
    FooBar::initialize(client_sock);
}

void handleClient(int client_sock) {
    char buffer[4096];
    while (true) {
        // Reset buffer to avoid potential garbage data
        memset(buffer, 0, sizeof(buffer));

        int expected_data_len = sizeof(buffer);
        int read_bytes = recv(client_sock, buffer, expected_data_len, 0);
        
        //until here it works good, the line is correct
        if (read_bytes == 0) {
            std::cout << "Connection closed" << std::endl;
            // Connection is closed
            break;
        } else if (read_bytes < 0) {
            perror("Error receiving from client");
            std::cout << "Error receiving from client" << std::endl;
            break;
        } else {
            buffer[read_bytes] = '\0';
            string line(buffer);
            FooBar::run(client_sock, line); 
            std::cout << "Data received: " << line << std::endl;
        }
    }
    //close(client_sock); not sure if i need to close the client_sock
}

int main() {
    std::cout << "TCPServer is running..." << std::endl;
    const int server_port = 5555;
    // Create a socket
    int sock = socket(AF_INET, SOCK_STREAM, 0);
    if (sock < 0) {
        perror("Error creating socket");
        return 1;
    }
    initBloomFilter(sock);
    //prepare to handle multiple clients
    while (true) {
        // The address structure for the client
        struct sockaddr_in client_sin;
        // The length of the address structure
        unsigned int addr_len = sizeof(client_sin);
        // Accept the connection
        int client_sock = accept(sock, (struct sockaddr *) &client_sin,
                &addr_len);
        if (client_sock < 0) {
            perror("Error accepting client");
            continue;
        }
        // Handle the client in a separate thread
        thread t(handleClient, client_sock);
        t.join(); // maybe detach instead of join
    }

    close(sock);
    return 0;
}