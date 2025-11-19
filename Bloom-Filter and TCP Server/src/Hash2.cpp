#include <iostream>
#include <string>
#include "IHash.h"
#include "Hash2.h"
using namespace std;

    //this function calculate the first hash function, and returns the result.
    int Hash2::startHashing (string URL , int sizeOfArray)  {
        // Instantiation of Object
        hash<string> hashFunction;
        size_t intHashResult = hashFunction(URL);
        string stringHashResult = to_string(intHashResult);
        intHashResult = hashFunction(stringHashResult);
        return (intHashResult%sizeOfArray);
    }
