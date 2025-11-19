#include <iostream>
#include <string>
#include "IHash.h"
#include "Hash1.h"
using namespace std;


    //this function calculate the first hash function, and returns the result.
    int Hash1::startHashing (string URL, int sizeOfArray) {
        // Instantiation of Object
        hash<string> hashFunction;
        size_t hashResult = hashFunction(URL);
        return (hashResult%sizeOfArray);
    }
