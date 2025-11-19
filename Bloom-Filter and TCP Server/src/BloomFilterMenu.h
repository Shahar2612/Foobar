#ifndef BLOOMFILTERMENU_H
#define BLOOMFILTERMENU_H
using namespace std;
#include "BloomFilter.h"
#include "IHash.h"
#include "ICommand.h"
#include <iostream>
#include <vector>
#include <map>

class BloomFilterMenu {
public:
    static void start(BloomFilter *bloomFilter, vector<IHash *> hash, int
    socket_fd, string line);

    static map<int, ICommand *> initCommandMenu();

    static void deleteCommands(map<int, ICommand *> commands);
};
#endif // BLOOMFILTERMENU_H