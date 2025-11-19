#include "BloomFilter.h"
#include "IHash.h"
#include "ICommand.h"
#include <iostream>
#include <vector>
#include <algorithm>
#include "SearchCommand.h"
#include <sys/socket.h>

void SearchCommand::execute(BloomFilter* bloomFilter, string URL, vector<IHash*> hash, int socket_fd) {
    int hashResult;
    for (int i = 0; i < hash.size(); i++) {
        hashResult = hash[i]->startHashing(URL, bloomFilter->getArrayOfBitsSize());
        if (bloomFilter->getArrayOfBits()[hashResult] == '0') {
            send(socket_fd, "false\n", 6, 0); // Write "false" to the socket
            return;
        }
    }
    // Check for false positive
    bool found = SearchCommand::searchInURLList(URL, bloomFilter->getUrlList());
    if (found) {
        send(socket_fd, "true true\n", 10, 0); // Write "true\n" to the socket
    } else {
        send(socket_fd, "true false\n", 11, 0); // Write "false\n" to the socket
    }
}



//searching a url in the url's list, returns true if he was found. works good
    bool SearchCommand::searchInURLList(const string& URL, vector <string> urlList) {
        return find(urlList.begin(), urlList.end(), URL) != urlList.end();
    }
