# Project-Part-1

Please note that Meitar Teper just returned from Miluim and therefore worked less on this part of the project than the others.

Our Work Process:
Jira:
We created a Jira project, started a two weeks sprint including issues and separation for epics.

TDD:
At first we wrote the tests we wanted our program to contain, thinking of the future methods and implementations.
Back and forth process, creating tests and then creating cpp classes, repairing tests, repairing classes etc.

Github:
We created a private repository and included all the project members, each working on his own computer using his own
user in git. The process went through the feature of branches principle, meaning everybody works on his branch and
eventually merging his branch to the main.

Docker:
Creating the file as required 

Refactors being made:
We started with the naive implementation as we thought was correct, gradually changing the structure of our program
to contain two interfaces: IHash and ICommand, thus using the encapsulation principle and hiding the implementation
from the user. Moreover, we used the separation of concerns principle, giving each class and method the minimum 
responsibility required for proper functioning.

How to compile and run our program:

Via Docker:
sudo docker build -t run_project .
sudo docker run -i run_project

Regular compile:
cmake --build build
./build/run_project

Test compile:
cmake --build build
./build/RunTests
