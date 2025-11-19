#include "InputCheck.h"
#include <iostream>
#include <sstream>
#include <vector>
#include <algorithm>
using namespace std;


    //this function checks if the first input is in the right format of two/three numbers.
    //and continue until it gets the right input.
    vector <int> InputCheck::firstInputCheck(string line) {
        vector <int>  inputNumbers;
        vector <int>  hashNumbers;
     //   bool untilValid = true;
        //run until the input is valid.
//        while (untilValid){
//            inputNumbers.clear();
//            //inputNumbers vector is 0 in the beginning of each iteration.
//            string line;
//            //get a line from the user.
//            getline(cin, line);
//                while (line.size() == 0){
//                    getline(cin, line);
//                    }
//
//                if (!(checkIfNotDigit(line))) {
//                    continue; // Invalid input, restart the loop
//                }

            istringstream iss(line);
            int firstNumber, secondNumber, number;
            // ((iss >> firstNumber >> secondNumber)
            // This line attempts to extract the first two numbers. It then checks whether the stream is at the end (iss.eof()),
            if (iss >> firstNumber >> secondNumber){
                inputNumbers.push_back(firstNumber);
                inputNumbers.push_back(secondNumber);
                hashNumbers.push_back(secondNumber);
                while(!(iss.eof())){
                    if (iss >> number){
                          const int& num = number;
                        if (find(hashNumbers.begin(), hashNumbers.end(), number) != hashNumbers.end()){
                            continue;
                        } 
             
                        else{
                             inputNumbers.push_back(number);
                        }
                    }
                    else{
                        break;
                    }
                }
            }
//            if (inputNumbers.size()==0){
//                continue;
//            }
//            if (!(InputCheck::checkIfValidsNumbers(inputNumbers))){
//                continue;
//            }
//            else{
//                // The input is valid
//                untilValid = false;
//             }
//        }
        return inputNumbers;
    }

    //this function check if the input is a char and not a digit.
    //not needed anymore because we set the initial values
     bool InputCheck::checkIfNotDigit (string line){
        if (line == " "){
            return false;
        }
        //check for non-numeric characters
            for (char ch : line) {
                if (ch == ' ') {
                    continue; // Ignore whitespace
                }
                if (!isdigit(ch)) {
                    return false;  // Found a non-numeric character
                }
            }
            return true;
    }

    //this function checks if the input numbers is in the correct range.
    bool InputCheck::checkIfValidsNumbers (vector <int> inputNumbers){
            int number;
            // Check if the array size is valid
            if (inputNumbers.at(0) <= 0) {
                return false;
            }
            //check if the rest of the input is valid
            for (int i=1;i<inputNumbers.size();i++) {
                number=inputNumbers.at(i);
                if (number != 1 && number != 2){
                    return false;
                }
            }
            return true;
    }

     //this function check the input of the user from the second time
     bool InputCheck::inputValidityCheck(string input)
    {
        //istringstream iss(input);: This line creates a std::istringstream object named
        // iss and initializes it with the input string (input).
        // The istringstream is like a stream, and we can use it to extract values from the string.
        istringstream iss(input);

        if(input[1] != ' '){
            return false;
        }

        int number;
        string URL;

        //(!(iss >> number >> url))  This line attempts to extract an integer
        // (number) followed by a string (url) from the istringstream (iss).
        if (!(iss >> number >> URL)) {
            return false;
        }

        // Check if the number at the beginning is 1 or 2
        if (number != 1 && number != 2) {
            return false;
        }

        // Check if the URL is non-empty
        if (URL.empty()) {
            return false;
        }

        // If all checks pass, the input is valid
        return true;
    }

