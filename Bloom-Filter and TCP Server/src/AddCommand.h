#ifndef ADDCOMMAND_H
#define ADDCOMMAND_H

#include "BloomFilter.h"
#include "IHash.h"
#include "ICommand.h"
#include <string>
#include <vector>

class AddCommand : public ICommand {
    public:
    void execute(BloomFilter* bloomFilter, string URL, vector <IHash*> hash,
                 int socket_fd) override;
};

#endif // ADDCOMMAND_H