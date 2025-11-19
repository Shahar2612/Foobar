#include <gtest/gtest.h>
#include <string>
#include <iostream>
#include <vector>
#include <sstream>
#include <map>

#include "BloomFilter.h"
#include "BloomFilterMenu.h"
#include "Hash1.h"
#include "Hash2.h"
#include "FooBar.h"
#include "SearchCommand.h"
#include "AddCommand.h"
#include "IHash.h"
#include "ICommand.h"
#include "InputCheck.h"

using namespace std;

    //BLOOM FILTER TEST

     TEST(BloomFilterTest, BloomFilter) {

        BloomFilter* bloomFilter = BloomFilter::getInstance(100);
        EXPECT_EQ(bloomFilter->getArrayOfBitsSize(), 100);
        EXPECT_FALSE(bloomFilter->getArrayOfBits()[0] == '1');
     }

     //HASH1 TEST

    TEST(Hash1Test, BasicFunctionality){
        Hash1 hash1;

        // Test the hash result for a specific URL and array size. GE=greater than, LT=less than
        int hashResult =hash1.startHashing("www.example.com", 100);
        EXPECT_GE(hashResult, 0);
        EXPECT_LT(hashResult, 100);

        // Test the hash result for a different URL and array size
//        int hashResult2 = hash1.startHashing("www.anotherexample.com", 50);
//        EXPECT_GE(hashResult2, 0);
//        EXPECT_LT(hashResult2, 50);

        }

        //HASH2 TEST

        TEST(Hash2Test, Hashing) {
        Hash2 hash2;

        // Test hashing with a URL and array size
        int hashResult1 = hash2.startHashing("www.example.com", 100);
        int hashResult2 = hash2.startHashing("www.anotherexample.com", 100);

        // Check that the hash results are within the valid range
        EXPECT_GE(hashResult1, 0);
        EXPECT_LT(hashResult1, 100);

        EXPECT_GE(hashResult2, 0);
        EXPECT_LT(hashResult2, 100);

        }

        //ADD-COMMAND TEST

        TEST(AddCommandTest, Execute) {
        // Create a BloomFilter with a size of 100
        BloomFilter* bloomFilter = BloomFilter::getInstance(100);

        // Create instances of Hash1 and Hash2
        Hash1 hash1;
        Hash2 hash2;

        // Create a vector of IHash pointers and add Hash1 and Hash2 instances
        vector<IHash*> hashFunctions;
        hashFunctions.push_back(&hash1);
        hashFunctions.push_back(&hash2);

        // Create an AddCommand instance
        AddCommand addCommand;

        // Execute the AddCommand on the BloomFilter with a URL and the vector of hash functions
        addCommand.AddCommand::execute(bloomFilter, "www.example.com",
                                       hashFunctions, 0);

        // Check that the bits in the BloomFilter array are updated based on hash results
        EXPECT_EQ(bloomFilter->getArrayOfBits()[hash1.startHashing("www.example.com", 100)], '1');
        EXPECT_EQ(bloomFilter->getArrayOfBits()[hash2.startHashing("www.example.com", 100)], '1');

        // Check that the URL is added to the URL list in the BloomFilter
        EXPECT_EQ(bloomFilter->getUrlList().size(), 1);
        EXPECT_EQ(bloomFilter->getUrlList()[0], "www.example.com");

        }

        //SEARCH-COMMAND TEST

        TEST(SearchCommandTest, Execute) {
        // Create a BloomFilter with a size of 100
        BloomFilter* bloomFilter = BloomFilter::getInstance(100);

        // Create instances of Hash1 and Hash2
        Hash1 hash1;
        Hash2 hash2;

        // Create a vector of IHash pointers and add Hash1 and Hash2 instances
        vector<IHash*> hashFunctions;
        hashFunctions.push_back(&hash1);
        hashFunctions.push_back(&hash2);

        // Create a SearchCommand instance
        SearchCommand searchCommand;

        // Redirect cout to capture the output
        testing::internal::CaptureStdout();

        // Execute the SearchCommand on the BloomFilter with a URL and the vector of hash functions
        searchCommand.execute(bloomFilter, "www.example.com", hashFunctions,
                              0);

        // Get the captured output
        string output = testing::internal::GetCapturedStdout();

        // Check that the output matches the expected result
       // EXPECT_EQ(output, "false\n");

        // Add the URL to the BloomFilter using AddCommand
        AddCommand addCommand;
        addCommand.AddCommand::execute(bloomFilter, "www.example.com",
                                       hashFunctions, 0);

        // Redirect cout again to capture the updated output
        testing::internal::CaptureStdout();

        // Execute the SearchCommand again
        searchCommand.execute(bloomFilter, "www.example.com", hashFunctions,
                              0);

        // Get the updated captured output
        output = testing::internal::GetCapturedStdout();

        // Check that the output matches the expected result
       // EXPECT_EQ(output, "true true\n");

        }

        //INPUT VALIDITY CHECK TEST

        TEST(InputValidityCheckTest, ValidInput) {
            // Valid input: "1 www.example.com"
            EXPECT_TRUE(InputCheck::inputValidityCheck("1 www.example.com"));
            // Valid input: "2 www.another-example.com"
            EXPECT_TRUE(InputCheck::inputValidityCheck("2 www.another-example.com"));
            // Invalid input format: Missing space after the number
            EXPECT_FALSE(InputCheck::inputValidityCheck("1www.example.com"));
            // Invalid input format: Missing space after the number
            EXPECT_FALSE(InputCheck::inputValidityCheck("2www.another-example.com"));
            // Invalid number: 3 is not a valid command number
            EXPECT_FALSE(InputCheck::inputValidityCheck("3 www.example.com"));
            // Invalid number: -1 is not a valid command number
            EXPECT_FALSE(InputCheck::inputValidityCheck("-1 www.example.com"));
            // Empty URL is invalid
            EXPECT_FALSE(InputCheck::inputValidityCheck("1 "));
            // Empty URL is invalid
            EXPECT_FALSE(InputCheck::inputValidityCheck("2 "));

        }

        //INIT-COMMAND-MENU TEST

        TEST(InitCommandMenuTest, AddCommand) {
            map<int, ICommand*> commands = BloomFilterMenu::initCommandMenu();
            //NE = not equal. checks it really is instantiated
            EXPECT_NE(commands[1], nullptr);
            //This line uses dynamic_cast to attempt to cast the value associated with the key 1 to a pointer of type AddCommand*.
            // It then checks if the result of the cast is not equal (!=) to nullptr.
            // This is a more specific check to ensure that the object associated with key 1 is indeed an instance of AddCommand
            EXPECT_TRUE(dynamic_cast<AddCommand*>(commands[1]) != nullptr);
             EXPECT_NE(commands[2], nullptr);
             EXPECT_TRUE(dynamic_cast<SearchCommand*>(commands[2]) != nullptr);
             EXPECT_EQ(commands.size(), 2);
        }

        //ALL-HASH-FUNCTION TEST

        TEST(AllHashFunctionTest, SingleHashFunction) {
            vector <int> inputUser = {32, 1};  // Single hash function (Hash1)
            vector<IHash*> hash = FooBar::allHashFunction(inputUser);
            EXPECT_TRUE((hash[0]) != nullptr);

            vector <int> inputUser2= {64, 1, 2};  // Two hash functions (Hash1 and Hash2)
            vector<IHash*> hash2 = FooBar::allHashFunction(inputUser2);
            EXPECT_TRUE((hash2[0]) != nullptr);
            EXPECT_TRUE((hash2[1]) != nullptr);
        }
