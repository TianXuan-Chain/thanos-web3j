package com.thanos.web3j.crypto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.thanos.common.utils.HashUtil;
import com.thanos.common.utils.Numeric;
import com.thanos.common.utils.rlp.RLP;
import com.thanos.web3j.model.ThanosEthTransaction;
import com.thanos.web3j.model.ThanosGlobalNodeEvent;
import com.thanos.web3j.protocol.core.methods.request.RawTransaction;
import com.thanos.web3j.rlp.RlpString;
import com.thanos.web3j.rlp.RlpType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

import static com.thanos.common.utils.ByteUtil.ZERO_BYTE_ARRAY;

/**
 * Create RLP encoded transaction, implementation as per p4 of the
 * <a href="http://gavwood.com/paper.pdf">yellow paper</a>.
 */
public class TransactionEncoder {
    private static Logger logger = LoggerFactory.getLogger(TransactionEncoder.class);
    private static Gson gson = new Gson();

    public static byte[] signMessage(RawTransaction rawTransaction, Credentials credentials) {
//        byte[] encodedTransaction = encode(rawTransaction);
//        byte[] signatureData = credentials.getSecureKey().sign(HashUtil.sha3(encodedTransaction));
//        return encode(rawTransaction, signatureData);

//        System.out.println("t=========: " + Hex.toHexString(encodedTransaction));
        ThanosEthTransaction transaction = ThanosEthTransaction.convertFrom(rawTransaction);
        byte[] signatureData = credentials.getSecureKey().sign(transaction.getHash());
        transaction.setSignature(signatureData);
        return transaction.getEncoded();
    }

    public static byte[] signGloabalNodeEvent(ThanosGlobalNodeEvent globalNodeEvent, Credentials credentials) {
//        System.out.println("t=========: " + Hex.toHexString(encodedTransaction));

        byte[] signatureData = credentials.getSecureKey().sign(globalNodeEvent.getHash());
        globalNodeEvent.setSignature(signatureData);
        return globalNodeEvent.getEncoded();
    }

    public static byte[] signMessageWithoutSign(RawTransaction rawTransaction, Credentials credentials) {
//        byte[] signatureData = ZERO_BYTE_ARRAY;
//        return encode(rawTransaction, signatureData);
        ThanosEthTransaction transaction = ThanosEthTransaction.convertFrom(rawTransaction);
        transaction.setSignature(ZERO_BYTE_ARRAY);
        return transaction.getEncoded();
    }

    public static byte[] signMessage(
            RawTransaction rawTransaction, byte chainId, Credentials credentials) {
        return signMessage(rawTransaction, credentials);
    }


    public static byte[] encode(RawTransaction rawTransaction) {
        return encode(rawTransaction, null);
    }


    private static byte[] encode(RawTransaction rawTransaction, byte[] signatureData) {
//        List<RlpType> values = asRlpValues(rawTransaction, signatureData);
        return asRlpEncoded(rawTransaction, signatureData);
    }

    static List<RlpType> asRlpValues(
            RawTransaction rawTransaction, byte[] signatureData) {
        List<RlpType> result = new ArrayList<>();
        result.add(RlpString.create(rawTransaction.getNonce()));
        result.add(RlpString.create(rawTransaction.getGasPrice()));
        result.add(RlpString.create(rawTransaction.getGasLimit()));
        // an empty to address (contract creation) should not be encoded as a numeric 0 value
        String to = rawTransaction.getTo();
        if (to != null && to.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
        } else {
            result.add(RlpString.create(""));
        }

        result.add(RlpString.create(rawTransaction.getValue()));

        byte[] data = Numeric.hexStringToByteArray(rawTransaction.getData());
        result.add(RlpString.create(data));

        Set<String> byteSet = rawTransaction.getExecuteStates();
        byte[] bytes = gson.toJson(byteSet).getBytes();
        result.add(RlpString.create(bytes));

        result.add(RlpString.create(rawTransaction.getPublicKey()));

        if (signatureData != null) {
            result.add(RlpString.create(signatureData));
        }
        return result;
    }

    static byte[] asRlpEncoded(RawTransaction rawTransaction, byte[] signatureData) {
        byte[] nonByte = RLP.encodeElement(rawTransaction.getNonce().toByteArray());
        byte[] gpByte = RLP.encodeElement(rawTransaction.getGasPrice().toByteArray());
        byte[] gsByte = RLP.encodeElement(rawTransaction.getGasLimit().toByteArray());

        // an empty to address (contract creation) should not be encoded as a numeric 0 value
        byte[] toByte = null;
        String to = rawTransaction.getTo();
        if (to != null && to.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
//            toByte = RLP.encodeElement(Numeric.hexStringToByteArray(to).clone());
            toByte = RLP.encodeElement(Hex.decode(to));
        } else {
            toByte = RLP.encodeElement("".getBytes());
        }

        byte[] valueByte = RLP.encodeElement(rawTransaction.getValue().toByteArray());

        byte[] data = Hex.decode(rawTransaction.getData());//Numeric.hexStringToByteArray(rawTransaction.getData());
        byte[] dataByte = RLP.encodeElement(RlpString.create(data).getBytes());

        Set<String> byteSet = rawTransaction.getExecuteStates();
        byte[] esByte = RLP.encodeStringSet(byteSet);

        Long futureEventNumber = rawTransaction.getFutureEventNumber();
        if (futureEventNumber == null) {
            futureEventNumber = -1l;
        }
        byte[] futureEventNumberByte = RLP.encodeBigInteger(BigInteger.valueOf(futureEventNumber));

        byte[] pkByte = RLP.encodeElement(RlpString.create(rawTransaction.getPublicKey()).getBytes());

        if (signatureData == null) {
//            System.out.println("ttt======: " + Hex.toHexString(nonByte) + ", " + Hex.toHexString(gpByte)
//                    + ", " + Hex.toHexString(gsByte) + ", " + Hex.toHexString(toByte)
//                    + ", " + Hex.toHexString(valueByte) + ", " + Hex.toHexString(dataByte)
//                    + "," + Hex.toHexString(esByte) + "," + Hex.toHexString(pkByte));
            return RLP.encodeList(nonByte, gpByte, gsByte, toByte, valueByte, dataByte, esByte, futureEventNumberByte, pkByte);
        } else {
            byte[] sd = RLP.encodeElement(RlpString.create(signatureData).getBytes());
            return RLP.encodeList(nonByte, gpByte, gsByte, toByte, valueByte, dataByte, esByte, futureEventNumberByte, pkByte, sd);
        }
    }

}
