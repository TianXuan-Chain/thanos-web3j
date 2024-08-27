pragma solidity ^0.4.25;

contract TokensDemo {

    function transfer(address source, address to, int32 tokens) public {
        assembly {
            let newsource := sub(sload(source), tokens)
            let newto := add(sload(to), tokens)
            sstore(source, newsource)
            sstore(to, newto)
        }

    }

    function setBalance(address source, int32 tokens) public {
        assembly {
            sstore(source, tokens)
        }
    }

    function getBalance(address source) view returns (int32 r) {
        assembly {
            r := sload(source)
        }
    }

}