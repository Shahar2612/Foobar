#include <iostream>
#include <vector>
#include <string>
#include "IHash.h"

using namespace std;

class BloomFilter {
private:
    static BloomFilter* instance;
    int arrayOfBitsSize;
    char* arrayOfBits;
    vector<string> urlList;
    vector<IHash*> hashList;

    BloomFilter(int size);

public:
    static BloomFilter* getInstance(int size);
    ~BloomFilter();
    int getArrayOfBitsSize();
    char* getArrayOfBits();
    vector<string>& getUrlList();
    vector<IHash*>& getHashList();
    void setHashList(const vector<IHash*>& newHashList);
};

BloomFilter* BloomFilter::instance = nullptr;

BloomFilter::BloomFilter(int size) : arrayOfBitsSize(size) {
    arrayOfBits = new char[size];
    // Initialize arrayOfBits with '0'
    for (int i = 0; i < size; ++i) {
        arrayOfBits[i] = '0';
    }
}

BloomFilter* BloomFilter::getInstance(int size) {
    if (instance == nullptr) {
        instance = new BloomFilter(size);
    }
    return instance;
}

BloomFilter::~BloomFilter() {
    delete[] arrayOfBits;
    // No need to delete urlList, as std::vector handles its own memory
}

int BloomFilter::getArrayOfBitsSize() {
    return arrayOfBitsSize;
}

char* BloomFilter::getArrayOfBits() {
    return arrayOfBits;
}

vector<string>& BloomFilter::getUrlList() {
    return urlList;
}

vector<IHash*>& BloomFilter::getHashList() {
    return hashList;
}

void BloomFilter::setHashList(const vector<IHash*>& newHashList) {
    hashList = newHashList;
}