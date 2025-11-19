#ifndef HASH1_H
#define HASH1_H

using namespace std;
class Hash1 : public IHash{
public:
    int startHashing(string URL, int sizeOfArray) override;
};
#endif // HASH1_H