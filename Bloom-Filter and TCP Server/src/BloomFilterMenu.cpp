#include "BloomFilter.h"
#include "IHash.h"
#include "ICommand.h"
#include "AddCommand.h"
#include "SearchCommand.h"
#include "BloomFilterMenu.h"
#include "InputCheck.h"
#include <iostream>
#include <sstream>
#include <vector>
#include <map>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>

using namespace std;

    void BloomFilterMenu :: start(BloomFilter * bloomFilter, vector <IHash*>
            hash, int socket_fd, string line){
        char buffer[4096]; // Buffer for reading data from the socket
        //bool foreverLoop = true;
        map <int, ICommand*> commands= BloomFilterMenu:: initCommandMenu();
       // while (foreverLoop){
       // memset(buffer, 0, sizeof(buffer)); // Clear buffer
//        int read_byte = recv(socket_fd, buffer, sizeof(buffer), 0);
//            string line(buffer);
            bool valid = InputCheck:: inputValidityCheck(line);
            if (!valid){
                //continue;
                return;
            }
            istringstream iss(line);
            int number;
            string URL;
            iss >> number >> URL;
            commands[number]->execute(bloomFilter,URL,hash, socket_fd);
        //}
      //  BloomFilterMenu::deleteCommands(commands);
    }


    //this function initialize a map that contains all the command that possible.
    map <int, ICommand*> BloomFilterMenu::initCommandMenu(){
        map <int, ICommand*> commands;
        ICommand * add = new AddCommand();
        commands [1] = add;
        ICommand * search = new SearchCommand();
        commands [2] = search;
        return commands;
    }

    //this function delete all the command that we create in the end.
    // it will iterate over the map using a range-based for loop.
    //'com.second' is the value
     void BloomFilterMenu:: deleteCommands(map <int, ICommand*> commands){
        for (const auto& com : commands) {
            delete com.second;
        }
    }