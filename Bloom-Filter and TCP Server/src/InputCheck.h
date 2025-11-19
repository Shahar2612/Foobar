#ifndef INPUTCHECK_H
#define INPUTCHECK_H

#include <string>
#include <vector>

class InputCheck {
public:
 static std::vector <int> firstInputCheck(std::string line);
 static bool checkIfNotDigit (std::string line);
 static bool inputValidityCheck(std::string input);
 static bool checkIfValidsNumbers (std::vector <int> inputNumbers);
};

#endif // INPUTCHECK_H
