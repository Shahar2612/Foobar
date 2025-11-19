// BloomFilter.h

#ifndef BLOOMFILTER_H
#define BLOOMFILTER_H

#include <iostream>
#include <vector>
#include <string>
#include "IHash.h"

class BloomFilter {
private:
    static BloomFilter* instance;
    int arrayOfBitsSize;
    char* arrayOfBits;
    vector<std::string> urlList;
    vector<IHash*> hashList;

    BloomFilter(int size); // Private constructor

public:
    static BloomFilter* getInstance(int size);
    int getArrayOfBitsSize();
    char* getArrayOfBits();
    vector<std::string>& getUrlList();
    vector<IHash*>& getHashList();
    void setHashList(const vector<IHash*>& newHashList);
};

#endif // BLOOMFILTER_H
