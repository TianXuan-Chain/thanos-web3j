pragma solidity ^0.4.0;

contract HelloWorld {
    event successEvent();

    string name;

    uint hellNum;

    function HelloWorld(){
        name = "Hi,Welcome!";
    }

    function get() constant returns (string){
        return name;
    }

    function set(string n){
        name = n;
        successEvent();
    }

    function setHello(uint n){
        hellNum = n;
        successEvent();
    }

    function getHelloNum() constant returns (uint){
        return hellNum;
    }
}