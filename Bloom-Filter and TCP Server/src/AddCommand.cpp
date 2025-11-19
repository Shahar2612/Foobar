
#include "ICommand.h"
#include "BloomFilter.h"
#include "AddCommand.h"
#include <vector>
#include <sys/socket.h>

using namespace std;

//this function run all the hash function and add the URL to the list.
//also the function update the bloomFilter acoording to the result of the hash function.
    void AddCommand :: execute(BloomFilter* bloomFilter, string URL, vector
            <IHash*> hash, int socket_fd )  {
        int hashResult;
        for (int i = 0; i< hash.size(); i++){
            hashResult = hash[i]->startHashing(URL, bloomFilter->getArrayOfBitsSize());
            bloomFilter->getArrayOfBits()[hashResult]='1';
        }

        bloomFilter->getUrlList().push_back(URL);

        // send back to the client that the URL was added
        send(socket_fd, "Added URL\n", 11, 0);
    }