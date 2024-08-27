//package test.jmeter;
//
//import com.thanos.web3j.abi.TypeReference;
//import com.thanos.web3j.abi.datatypes.Address;
//import com.thanos.web3j.abi.datatypes.Function;
//import com.thanos.web3j.abi.datatypes.Type;
//import com.thanos.web3j.abi.datatypes.Uint;
//import com.thanos.web3j.crypto.Credentials;
//import com.thanos.web3j.crypto.ECKeyPair;
//import com.thanos.web3j.crypto.Keys;
//import com.thanos.web3j.protocol.Web3j;
//import com.thanos.web3j.protocol.exceptions.TransactionTimeoutException;
//import com.thanos.web3j.protocol.http.HttpService;
//import org.apache.jmeter.config.Arguments;
//import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
//import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
//import org.apache.jmeter.samplers.SampleResult;
//import test.DefaultContract;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//public class JmeterSendTransactionLocal extends AbstractJavaSamplerClient {
//    public static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JmeterSendTransactionLocal.class);
//
//    private static List<Web3j> web3jList = new ArrayList<Web3j>();
//    private static Credentials credentials;
//
//    static {
//        List<String> nodeListStr = Arrays.asList(
//                "http://10.177.96.143:8087/rpc",
//                "http://10.177.96.143:8087/rpc",
//                "http://10.177.96.143:8087/rpc",
//                "http://10.177.96.143:8087/rpc");
//        Web3j web3j = null;
//        for (String str : nodeListStr) {
//            web3j = Web3j.build(new HttpService(str));
//            web3jList.add(web3j);
//        }
//    }
//
//    @Override
//    //设置默认参数
//    public Arguments getDefaultParameters() {
//        Arguments arguments = new Arguments();
//        arguments.addArgument("name", "zhangsan");
//        return arguments;
//    }
//
//    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
//        SampleResult sr = new SampleResult();
//        sr.sampleStart();
//
//        String str = "发送成功: " + test();
//
//        //设置响应数据
//        sr.setResponseData(str, "UTF-8");
//        sr.setDataType(SampleResult.TEXT);
//        sr.setSuccessful(true);
//        sr.sampleEnd();
//        return sr;
//    }
//
//
//    private int test() {
//        Long data = new Random().nextLong();
//        int num = Math.abs(data.intValue());
//        int k = num % 4;
//        Web3j web3j = web3jList.get(k);
//
//        try {
//            ECKeyPair keyPair = Keys.createEcKeyPair();
//            credentials = Credentials.create(keyPair);
//        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        }
//
//        DefaultContract defaultContract = new DefaultContract("", "0x100", web3j, credentials, new BigInteger("0"), new BigInteger("3000000"));
//        Function function = new Function("transfer",
//                Arrays.<Type>asList(new Address("0x0"), new Uint(BigInteger.valueOf(num))),
//                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
//                }, new TypeReference<Uint>() {
//                }));
//        try {
////            long s1 = System.currentTimeMillis();
//            defaultContract.executeTransaction(function);
////            long s2 = System.currentTimeMillis();
////            System.out.println(s2 - s1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TransactionTimeoutException e) {
//            e.printStackTrace();
//        }
//        return new Random().nextInt(1000000000);
//    }
//
//    // main只是为了调试用，最后打jar包的时候注释掉。
////    public static void main(String[] args) {
////        JavaSamplerContext arg0 = new JavaSamplerContext(new Arguments());
////        JmeterSendTransactionLocal test = new JmeterSendTransactionLocal();
////        for (int i = 0; i < 10000; i++) {
////
////            SampleResult sr = test.runTest(arg0);
////
////            System.out.println(sr.getResponseDataAsString());
////        }
////    }
//
//}
