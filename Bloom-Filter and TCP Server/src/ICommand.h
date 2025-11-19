#ifndef ICOMMAND_H
#define ICOMMAND_H
#include "BloomFilter.h"
#include "IHash.h"
#include <vector>
// Forward declaration
class AddCommand;
class SearchCommand;

//this is a virtual class.
class ICommand {
public:
   virtual void execute(BloomFilter *bloomFilter, string URL, vector<IHash *>
           hash, int socket_fd) = 0;
};
#endif // ICOMMAND_H