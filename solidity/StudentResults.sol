// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract StudentResults {
    address public owner;
    mapping(address => bool) public admins;

    struct StudentRecord {
        bytes32 studentIdHash;
        bytes32 cnicHash;
        string ipfsHash;
        uint48 createdAt;
        uint48 updatedAt;
        bool exists;
    }

    mapping(bytes32 => StudentRecord) private studentResults;
    mapping(bytes32 => bytes32) private cnicToStudentId; // link CNIC hash → StudentId hash

    event AdminAdded(address indexed newAdmin);
    event AdminRemoved(address indexed removedAdmin);
    event ResultStored(
        bytes32 indexed studentIdHash,
        bytes32 indexed cnicHash,
        string ipfsHash,
        uint256 timestamp
    );
    event ResultUpdated(
        bytes32 indexed studentIdHash,
        string newIpfsHash,
        uint256 timestamp
    );
    event ResultDeleted(bytes32 indexed studentIdHash, uint256 timestamp);

    modifier onlyOwner() {
        require(msg.sender == owner, "Only owner");
        _;
    }

    modifier onlyAdmin() {
        require(admins[msg.sender] || msg.sender == owner, "Only admin");
        _;
    }

    // ---------------- CONSTRUCTOR ----------------
    constructor() {
        owner = msg.sender;
        admins[msg.sender] = true;
    }

    // ---------------- ADMIN FUNCTIONS ----------------
    function addAdmin(address _admin) external onlyOwner {
        require(!admins[_admin], "Already admin");
        admins[_admin] = true;
        emit AdminAdded(_admin);
    }

    function removeAdmin(address _admin) external onlyOwner {
        require(admins[_admin], "Not admin");
        admins[_admin] = false;
        emit AdminRemoved(_admin);
    }

    // ---------------- MAIN FUNCTIONS ----------------
    function storeResult(
        string memory _studentId,
        string memory _cnic,
        string memory _ipfsHash
    ) external onlyAdmin {
        bytes32 studentIdHash = keccak256(abi.encodePacked(_studentId));
        bytes32 cnicHash = keccak256(abi.encodePacked(_cnic));

        require(!studentResults[studentIdHash].exists, "Already exists");

        StudentRecord memory record = StudentRecord({
            studentIdHash: studentIdHash,
            cnicHash: cnicHash,
            ipfsHash: _ipfsHash,
            createdAt: uint48(block.timestamp),
            updatedAt: uint48(block.timestamp),
            exists: true
        });

        studentResults[studentIdHash] = record;
        cnicToStudentId[cnicHash] = studentIdHash;

        emit ResultStored(studentIdHash, cnicHash, _ipfsHash, block.timestamp);
    }

    function updateResult(
        string memory _studentId,
        string memory _newIpfsHash
    ) external onlyAdmin {
        bytes32 studentIdHash = keccak256(abi.encodePacked(_studentId));
        require(studentResults[studentIdHash].exists, "Record not found");

        studentResults[studentIdHash].ipfsHash = _newIpfsHash;
        studentResults[studentIdHash].updatedAt = uint48(block.timestamp);

        emit ResultUpdated(studentIdHash, _newIpfsHash, block.timestamp);
    }

    function deleteResult(string memory _studentId) external onlyAdmin {
        bytes32 studentIdHash = keccak256(abi.encodePacked(_studentId));
        require(studentResults[studentIdHash].exists, "Not found");

        delete cnicToStudentId[studentResults[studentIdHash].cnicHash];
        delete studentResults[studentIdHash];

        emit ResultDeleted(studentIdHash, block.timestamp);
    }

    // ---------------- VIEW FUNCTIONS ----------------
    function getResult(
        string memory _studentId
    ) external view returns (StudentRecord memory) {
        bytes32 studentIdHash = keccak256(abi.encodePacked(_studentId));
        return studentResults[studentIdHash];
    }

    function getResultByCnic(
        string memory _cnic
    ) external view returns (StudentRecord memory) {
        bytes32 cnicHash = keccak256(abi.encodePacked(_cnic));
        bytes32 studentIdHash = cnicToStudentId[cnicHash];
        return studentResults[studentIdHash];
    }
}
