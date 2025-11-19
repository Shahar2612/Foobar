#include "BloomFilter.h"
#include "IHash.h"
#include "Hash1.h"
#include "Hash2.h"
#include "BloomFilterMenu.h"
#include "FooBar.h"
#include "InputCheck.h"
#include <iostream>
#include <vector>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>

using namespace std;

    void FooBar::initialize(int socket_fd){
        char buffer[256];
        memset(buffer, 0, sizeof(buffer)); // Clear buffer
        if (read(socket_fd, buffer, sizeof(buffer)) < 0) {
            perror("Error reading from socket");
        }
        string line(buffer);
        std::cout << "Received: " << line << std::endl;
        vector <int> inputUser = InputCheck::firstInputCheck(line);
        BloomFilter* bloomFilter = BloomFilter::getInstance(inputUser.at(0));
        vector <IHash*> hash= allHashFunction(inputUser);
        bloomFilter->setHashList(hash);
    }

     void FooBar::run(int socket_fd, string line){
        //the size doesnt matter because we already initialize the bloomFilter
        //i just need to get the instance of the bloomFilter.
         BloomFilter* bloomFilter = BloomFilter::getInstance(0);
        BloomFilterMenu::start(bloomFilter,bloomFilter->getHashList(),
                               socket_fd, line);

        //in the end delete all hash function
      //  deleteAllHashFunction(hash);
        //in the end - delete bloomFilter that was created on the heap.
     //   delete bloomFilter;

    }

    //this function delete all the function that we create in the end.
    // it will iterate over the vector using a range-based for loop.
     void FooBar:: deleteAllHashFunction(vector <IHash*> hash){
        for (auto& function : hash) {
            delete function;
        }
    }



    // this function initialize the hash vector with the hash function we eill use in
    // the corrent bloomFilter.
     vector <IHash*> FooBar::allHashFunction (vector <int> inputUser){
        vector <IHash*> hash;
        for (int i =1; i<inputUser.size();i++){
            if (inputUser.at(i)==1){
                hash.push_back(new Hash1());
                continue;
            }
            if (inputUser.at(i)==2){
                hash.push_back(new Hash2());
            }
        }

        return hash;
    }

