#ifndef IHASH_H
#define IHASH_H

#include <string>
using namespace std;
// Forward declaration
class Hash1;
class Hash2;

//this is a virtual class.
class IHash {

public:
 virtual int startHashing(string URL, int sizeOfArray) = 0;
};

#endif // IHASH_H