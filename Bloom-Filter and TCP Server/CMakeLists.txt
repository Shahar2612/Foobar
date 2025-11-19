cmake_minimum_required(VERSION 3.14)
project("Project Part 1")
set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
include(FetchContent)

FetchContent_Declare(
        googletest
        URL
        https://github.com/google/googletest/archive/03597a01ee50ed
        33e9dfd640b249b4be3799d395.zip
)
enable_testing()

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -g")

set(SOURCE_FILES
        src/Main.cpp
        src/AddCommand.cpp
        src/BloomFilter.cpp
        src/BloomFilterMenu.cpp
        src/FooBar.cpp
        src/Hash1.cpp
        src/Hash2.cpp
        src/SearchCommand.cpp
        src/InputCheck.cpp
        src/TCPServer.cpp
)

set(SOURCE_FILES_TEST
        src/AddCommand.cpp
        src/BloomFilter.cpp
        src/BloomFilterMenu.cpp
        src/FooBar.cpp
        src/Hash1.cpp
        src/Hash2.cpp
        src/SearchCommand.cpp
        src/InputCheck.cpp

)

set(TEST_FILES
        tests/test_main.cpp # this is the main for tests
        tests/testsPart1.cpp
)

#compile all the cpp files
add_executable(run_project ${SOURCE_FILES})
#include all the headers
target_include_directories(run_project PRIVATE ${CMAKE_CURRENT_SOURCE_DIR}/src)



add_executable(RunTests ${SOURCE_FILES_TEST} ${TEST_FILES})
# Link Google Test to the test executable
target_link_libraries(RunTests PRIVATE gtest gtest_main)
# Include the source directory for tests
target_include_directories(RunTests PRIVATE src)
include(GoogleTest)
add_test(NAME RunTests COMMAND RunTests)