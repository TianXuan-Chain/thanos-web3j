pragma solidity >=0.4.11 <0.6.0;

contract add {
    uint c;
    function test(uint a, uint b) returns (uint){
        c = a + b;
        return c;
    }
}
