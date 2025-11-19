#ifndef HASH2_H
#define HASH2_H

using namespace std;
class Hash2 : public IHash{
public:
    int startHashing(string URL, int sizeOfArray) override;
};
#endif // HASH2_H