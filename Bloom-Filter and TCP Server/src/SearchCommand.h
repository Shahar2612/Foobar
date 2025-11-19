#ifndef ISEARCHCOMMAND_H
#define ISEARCHCOMMAND_H
using namespace std;
#include "ICommand.h"
#include "BloomFilter.h"
#include "IHash.h"
#include <vector>

class SearchCommand: public ICommand {
public:
    void execute(BloomFilter *bloomFilter, string URL, vector<IHash *> hash, int socket_fd)
    override;

    bool searchInURLList(const string &URL, vector<string> urlList);
};
#endif // ISEARCHCOMMAND_H