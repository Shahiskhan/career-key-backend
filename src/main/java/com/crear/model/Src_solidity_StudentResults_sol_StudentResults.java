package com.crear.model;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint48;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.8.7.
 */
@SuppressWarnings("rawtypes")
public class Src_solidity_StudentResults_sol_StudentResults extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ADDADMIN = "addAdmin";

    public static final String FUNC_ADMINS = "admins";

    public static final String FUNC_DELETERESULT = "deleteResult";

    public static final String FUNC_GETRESULT = "getResult";

    public static final String FUNC_GETRESULTBYCNIC = "getResultByCnic";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REMOVEADMIN = "removeAdmin";

    public static final String FUNC_STORERESULT = "storeResult";

    public static final String FUNC_UPDATERESULT = "updateResult";

    public static final Event ADMINADDED_EVENT = new Event("AdminAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event ADMINREMOVED_EVENT = new Event("AdminRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event RESULTDELETED_EVENT = new Event("ResultDeleted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event RESULTSTORED_EVENT = new Event("ResultStored", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event RESULTUPDATED_EVENT = new Event("ResultUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Src_solidity_StudentResults_sol_StudentResults(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Src_solidity_StudentResults_sol_StudentResults(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Src_solidity_StudentResults_sol_StudentResults(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Src_solidity_StudentResults_sol_StudentResults(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<AdminAddedEventResponse> getAdminAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADMINADDED_EVENT, transactionReceipt);
        ArrayList<AdminAddedEventResponse> responses = new ArrayList<AdminAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AdminAddedEventResponse typedResponse = new AdminAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AdminAddedEventResponse> adminAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AdminAddedEventResponse>() {
            @Override
            public AdminAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADMINADDED_EVENT, log);
                AdminAddedEventResponse typedResponse = new AdminAddedEventResponse();
                typedResponse.log = log;
                typedResponse.newAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AdminAddedEventResponse> adminAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADMINADDED_EVENT));
        return adminAddedEventFlowable(filter);
    }

    public List<AdminRemovedEventResponse> getAdminRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADMINREMOVED_EVENT, transactionReceipt);
        ArrayList<AdminRemovedEventResponse> responses = new ArrayList<AdminRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AdminRemovedEventResponse typedResponse = new AdminRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.removedAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AdminRemovedEventResponse> adminRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AdminRemovedEventResponse>() {
            @Override
            public AdminRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADMINREMOVED_EVENT, log);
                AdminRemovedEventResponse typedResponse = new AdminRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.removedAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AdminRemovedEventResponse> adminRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADMINREMOVED_EVENT));
        return adminRemovedEventFlowable(filter);
    }

    public List<ResultDeletedEventResponse> getResultDeletedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RESULTDELETED_EVENT, transactionReceipt);
        ArrayList<ResultDeletedEventResponse> responses = new ArrayList<ResultDeletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ResultDeletedEventResponse typedResponse = new ResultDeletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.studentIdHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ResultDeletedEventResponse> resultDeletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ResultDeletedEventResponse>() {
            @Override
            public ResultDeletedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RESULTDELETED_EVENT, log);
                ResultDeletedEventResponse typedResponse = new ResultDeletedEventResponse();
                typedResponse.log = log;
                typedResponse.studentIdHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ResultDeletedEventResponse> resultDeletedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RESULTDELETED_EVENT));
        return resultDeletedEventFlowable(filter);
    }

    public List<ResultStoredEventResponse> getResultStoredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RESULTSTORED_EVENT, transactionReceipt);
        ArrayList<ResultStoredEventResponse> responses = new ArrayList<ResultStoredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ResultStoredEventResponse typedResponse = new ResultStoredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.studentIdHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.cnicHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.ipfsHash = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ResultStoredEventResponse> resultStoredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ResultStoredEventResponse>() {
            @Override
            public ResultStoredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RESULTSTORED_EVENT, log);
                ResultStoredEventResponse typedResponse = new ResultStoredEventResponse();
                typedResponse.log = log;
                typedResponse.studentIdHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.cnicHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.ipfsHash = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ResultStoredEventResponse> resultStoredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RESULTSTORED_EVENT));
        return resultStoredEventFlowable(filter);
    }

    public List<ResultUpdatedEventResponse> getResultUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RESULTUPDATED_EVENT, transactionReceipt);
        ArrayList<ResultUpdatedEventResponse> responses = new ArrayList<ResultUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ResultUpdatedEventResponse typedResponse = new ResultUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.studentIdHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newIpfsHash = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ResultUpdatedEventResponse> resultUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ResultUpdatedEventResponse>() {
            @Override
            public ResultUpdatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RESULTUPDATED_EVENT, log);
                ResultUpdatedEventResponse typedResponse = new ResultUpdatedEventResponse();
                typedResponse.log = log;
                typedResponse.studentIdHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newIpfsHash = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ResultUpdatedEventResponse> resultUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RESULTUPDATED_EVENT));
        return resultUpdatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addAdmin(String _admin) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _admin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> admins(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADMINS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> deleteResult(String _studentId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DELETERESULT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_studentId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<StudentRecord> getResult(String _studentId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETRESULT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_studentId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StudentRecord>() {}));
        return executeRemoteCallSingleValueReturn(function, StudentRecord.class);
    }

    public RemoteFunctionCall<StudentRecord> getResultByCnic(String _cnic) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETRESULTBYCNIC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_cnic)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StudentRecord>() {}));
        return executeRemoteCallSingleValueReturn(function, StudentRecord.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeAdmin(String _admin) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _admin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> storeResult(String _studentId, String _cnic, String _ipfsHash) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STORERESULT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_studentId), 
                new org.web3j.abi.datatypes.Utf8String(_cnic), 
                new org.web3j.abi.datatypes.Utf8String(_ipfsHash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateResult(String _studentId, String _newIpfsHash) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATERESULT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_studentId), 
                new org.web3j.abi.datatypes.Utf8String(_newIpfsHash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Src_solidity_StudentResults_sol_StudentResults load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Src_solidity_StudentResults_sol_StudentResults(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Src_solidity_StudentResults_sol_StudentResults load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Src_solidity_StudentResults_sol_StudentResults(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Src_solidity_StudentResults_sol_StudentResults load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Src_solidity_StudentResults_sol_StudentResults(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Src_solidity_StudentResults_sol_StudentResults load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Src_solidity_StudentResults_sol_StudentResults(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class StudentRecord extends DynamicStruct {
        public byte[] studentIdHash;

        public byte[] cnicHash;

        public String ipfsHash;

        public BigInteger createdAt;

        public BigInteger updatedAt;

        public Boolean exists;

        public StudentRecord(byte[] studentIdHash, byte[] cnicHash, String ipfsHash, BigInteger createdAt, BigInteger updatedAt, Boolean exists) {
            super(new org.web3j.abi.datatypes.generated.Bytes32(studentIdHash),new org.web3j.abi.datatypes.generated.Bytes32(cnicHash),new org.web3j.abi.datatypes.Utf8String(ipfsHash),new org.web3j.abi.datatypes.generated.Uint48(createdAt),new org.web3j.abi.datatypes.generated.Uint48(updatedAt),new org.web3j.abi.datatypes.Bool(exists));
            this.studentIdHash = studentIdHash;
            this.cnicHash = cnicHash;
            this.ipfsHash = ipfsHash;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.exists = exists;
        }

        public StudentRecord(Bytes32 studentIdHash, Bytes32 cnicHash, Utf8String ipfsHash, Uint48 createdAt, Uint48 updatedAt, Bool exists) {
            super(studentIdHash,cnicHash,ipfsHash,createdAt,updatedAt,exists);
            this.studentIdHash = studentIdHash.getValue();
            this.cnicHash = cnicHash.getValue();
            this.ipfsHash = ipfsHash.getValue();
            this.createdAt = createdAt.getValue();
            this.updatedAt = updatedAt.getValue();
            this.exists = exists.getValue();
        }
    }

    public static class AdminAddedEventResponse extends BaseEventResponse {
        public String newAdmin;
    }

    public static class AdminRemovedEventResponse extends BaseEventResponse {
        public String removedAdmin;
    }

    public static class ResultDeletedEventResponse extends BaseEventResponse {
        public byte[] studentIdHash;

        public BigInteger timestamp;
    }

    public static class ResultStoredEventResponse extends BaseEventResponse {
        public byte[] studentIdHash;

        public byte[] cnicHash;

        public String ipfsHash;

        public BigInteger timestamp;
    }

    public static class ResultUpdatedEventResponse extends BaseEventResponse {
        public byte[] studentIdHash;

        public String newIpfsHash;

        public BigInteger timestamp;
    }
}
