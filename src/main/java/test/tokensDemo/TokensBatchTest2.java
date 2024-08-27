//package test.tokensDemo;
//
//import com.thanos.common.crypto.key.asymmetric.SecureKey;
//import com.thanos.web3j.abi.datatypes.Address;
//import com.thanos.web3j.abi.datatypes.generated.Int32;
//import com.thanos.web3j.crypto.Credentials;
//import com.thanos.web3j.crypto.TransactionEncoder;
//import com.thanos.web3j.protocol.Web3j;
//import com.thanos.web3j.protocol.core.methods.request.RawTransaction;
//import com.thanos.web3j.protocol.core.methods.response.EthSendTransactionList;
//import com.thanos.web3j.protocol.exceptions.TransactionTimeoutException;
//import com.thanos.web3j.protocol.http.OkHttpService;
//import com.thanos.web3j.tx.RawTransactionManager;
//import com.thanos.web3j.utils.Numeric;
//import org.apache.commons.lang3.StringUtils;
//import org.spongycastle.util.encoders.Hex;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.security.SecureRandom;
//import java.util.*;
//import java.util.concurrent.*;
//
//public class TokensBatchTest2 {
//
//    private static List<Web3j> web3jList = new ArrayList<Web3j>();
//    private static Credentials credentials;
//
//    static {
//        List<String> nodeListStr = Arrays.asList(
////                "http://127.0.0.1:8080/rpc",
////                "http://127.0.0.1:8080/rpc",
////                "http://127.0.0.1:8080/rpc"
//                "http://10.170.164.227:7087/rpc",
//                "http://10.130.46.141:7088/rpc"
////                "http://10.170.164.227:7089/rpc"
//        );
//        Web3j web3j = null;
//        for (String str : nodeListStr) {
//            web3j = Web3j.build(new OkHttpService(str));
//            web3jList.add(web3j);
//        }
//        try {
////            SecureKey secureKey = SecureKey.getInstance();
////            credentials = Credentials.create(secureKey);
//            SecureKey sender = SecureKey.fromPrivate(Hex.decode("0100013ec771c31cac8c0dba77a69e503765701d3c2bb62435888d4ffa38fed60c445c"));
//            credentials = Credentials.create(sender);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(30);
//    private static ThreadPoolExecutor push = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
//
//    private static List<List<String>> txsPackageDeque = new ArrayList<>(35);
//
//    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException, TransactionTimeoutException {
//        Web3j web3j_0 = web3jList.get(0);
//        Web3j web3j_1 = web3jList.get(1);
////        Web3j web3j_2 = web3jList.get(2);
//
//        String test = "hehe";
//        Set<String> testSet = new HashSet<>();
//        testSet.add(test);
//
//        TokensDemo.executeStatesByDeploy = testSet;
//        TokensDemo dp = TokensDemo.deploy(web3j_0, credentials, BigInteger.valueOf(1), BigInteger.valueOf(3000000), BigInteger.valueOf(0)).get();
//        String address = dp.getContractAddress();
//        System.out.println(address);
//
//        send(web3j_0, web3j_1, null);
//
//        for (int n = 0; n < 35; n++) {
//
//            long s1 = System.currentTimeMillis();
//
//            List<String> rawStringList = new ArrayList<>(200000);
//
//            CountDownLatch latch = new CountDownLatch(200000);
//            //给用户设置值，15w笔
//
//            for (int i = 0; i < 200000; i++) {
//
//                final int k = i;
//                executor.execute(() -> {
//                    try {
//
//                        SecureKey secureKey_1 = SecureKey.getInstance("ECDSA", 1);
//                        Credentials user_1 = Credentials.create(secureKey_1);
//                        Set<String> exeSta = new HashSet<>();
//                        exeSta.add(user_1.getAddress());
//
//                        String setBalanceData = TokensDemo.setBalanceData(new Address(user_1.getAddress()), new Int32(1000000));
//                        Random r = new SecureRandom();
//                        BigInteger randomid = new BigInteger(250, r);
//                        Long consensusNumber = 100l;//transactionManager.getThanosLatestConsensusNumber();
//                        RawTransaction rawTransaction = RawTransaction.createTransaction(
//                                randomid,
//                                BigInteger.valueOf(1),
//                                BigInteger.valueOf(3000000),
//                                address,
//                                setBalanceData,
//                                exeSta, consensusNumber + 5);
//                        rawTransaction.setPublicKey(credentials.getSecureKey().getPubKey());
//                        rawTransaction.setCredentials(credentials);
//
//                        //签名
//                        byte[] signedMessage = TransactionEncoder.signMessageWithoutSign(rawTransaction, rawTransaction.getCredentials());
//                        String hexValue = Numeric.toHexString(signedMessage);
//                        if (StringUtils.isNotBlank(hexValue)) {
//                            rawStringList.add(hexValue);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        latch.countDown();
//                    }
//                    if (k % 10000 == 0) {
//                        System.out.println("number" + " : " + k);
//                    }
//                });
//            }
//
//            latch.await();
//
//            txsPackageDeque.add(rawStringList);
//
//            System.out.println("============================================================");
//            System.out.println("round: " + n + " ,time: " + (System.currentTimeMillis() - s1));
//
//        }
//
//    }
//
//    public static void send(Web3j web3j_0, Web3j web3j_1, Web3j web3j_2) {
//        RawTransactionManager transactionManager_0 = new RawTransactionManager(web3j_0, credentials);
//        RawTransactionManager transactionManager_1 = new RawTransactionManager(web3j_1, credentials);
////        RawTransactionManager transactionManager_2 = new RawTransactionManager(web3j_2, credentials);
//
//        new Thread(() -> {
//            int i = 0;
//            while (true) {
//                try {
//                    if (txsPackageDeque.size() < 35) {
//                        Thread.sleep(5000);
//                        continue;
//                    }
//                    int n = i % 35;
//                    List<String> rawStringList = txsPackageDeque.get(n);
//                    i++;
//                    int size = rawStringList.size();
//                    CountDownLatch latch = new CountDownLatch(2);
//                    push.execute(() -> {
//                        try {
//                            List<String> rawTransactions_0 = rawStringList.subList(0, 100000);
//                            EthSendTransactionList res_0 = transactionManager_0.SendTransactionListWithoutSign(rawTransactions_0);
//                            System.out.println("node0 ============================");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        } finally {
//                            latch.countDown();
//                        }
//                    });
//                    push.execute(() -> {
//                        try {
//                            List<String> rawTransactions_1 = rawStringList.subList(100000, size);
//                            EthSendTransactionList res_1 = transactionManager_1.SendTransactionListWithoutSign(rawTransactions_1);
//                            System.out.println("node1 ============================");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        } finally {
//                            latch.countDown();
//                        }
//                    });
//                    latch.await();
////                    push.execute(() -> {
////                        try {
////                            List<String> rawTransactions_2 = rawStringList.subList(200000, size);
////                            EthSendTransactionList res_2 = transactionManager_2.SendTransactionListWithoutSign(rawTransactions_2);
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
////                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//
//    //获取值
////        int number = 0;
////        for (Credentials ct : userList) {
////            Function function = TokensDemo.getBalanceData(new Address(ct.getAddress()));
////            ThanosTransactionReceipt receipt = transactionManager_0.thanosEthCall(BigInteger.valueOf(1),
////                    BigInteger.valueOf(3000000),
////                    address,
////                    FunctionEncoder.encode(function),
////                    BigInteger.ZERO,
////                    testSet);
////            String value = Hex.toHexString(receipt.getExecutionResult());
////            List<Type> f = FunctionReturnDecoder.decode(value, function.getOutputParameters());
////            BigInteger b = ((Int32) f.get(0)).getValue();
////            if (10000 != b.intValue()) {
////                Thread.sleep(10000);
////            }
////            System.out.println(number + " : " + ct.getAddress() + " : " + b);
////            number++;
////        }
//
//
////        //给用户设置值，9w笔
////        List<RawTransaction> rawTransactions_tf = new ArrayList<>();
////        Set<String> exeSta;
////        Credentials user_t = userList.get(0);
////        for (int i = 1; i < 90000; i++) {
////
////            Credentials user_r = userList.get(i);
////
////            String data = TokensDemo.transferData(new Address(user_t.getAddress()), new Address(user_r.getAddress()), new Int32(1));
////
////            exeSta = new HashSet<>();
////            exeSta.add(user_t.getAddress());
////            exeSta.add(user_r.getAddress());
////
////            Random r = new SecureRandom();
////            BigInteger randomid = new BigInteger(250, r);
////            RawTransaction rawTransaction = RawTransaction.createTransaction(
////                    randomid,
////                    BigInteger.valueOf(1),
////                    BigInteger.valueOf(3000000),
////                    address,
////                    data,
////                    exeSta);
////            rawTransaction.setPublicKey(credentials.getSecureKey().getPubKey());
////            rawTransaction.setCredentials(credentials);
////            rawTransactions.add(rawTransaction);
////            System.out.println("number" + " : " + i);
////        }
//
//}
