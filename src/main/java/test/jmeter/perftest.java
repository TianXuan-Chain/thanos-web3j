//package test.jmeter;
//
//import org.apache.jmeter.config.Arguments;
//import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
//import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
//import org.apache.jmeter.samplers.SampleResult;
//
//public class perftest extends AbstractJavaSamplerClient {
//
//    /**
//     * Holds the result data (shown as Response Data in the Tree display).
//     */
//
//    private String resultData;
//
//    // 这个方法是用来自定义java方法入参的。
//    // params.addArgument("num1","");表示入参名字叫num1，默认值为空。
//    //设置可用参数及的默认值；
//    public Arguments getDefaultParameters() {
//        Arguments params = new Arguments();
//        params.addArgument("num1", "");
//        params.addArgument("num2", "");
//        return params;
//
//    }
//
//    //开始测试，从arg0参数可以获得参数值；
//    public SampleResult runTest(JavaSamplerContext arg0) {
//        SampleResult sr = new SampleResult();
//        sr.setSampleLabel("Java请求哦");
//        try {
//            sr.sampleStart();// jmeter 开始统计响应时间标记
//            resultData = String.valueOf(100);
//            if (resultData != null && resultData.length() > 0) {
//                sr.setResponseData("结果是：" + resultData, null);
//                sr.setDataType(SampleResult.TEXT);
//            }
//            sr.setSuccessful(true);
//        } catch (Throwable e) {
//            sr.setSuccessful(false);
//            e.printStackTrace();
//        } finally {
//            sr.sampleEnd();// jmeter 结束统计响应时间标记
//        }
//        return sr;
//    }
//
//    // main只是为了调试用，最后打jar包的时候注释掉。
////    public static void main(String[] args) {
////        Arguments params = new Arguments();
////        params.addArgument("num1", "1");//设置参数，并赋予默认值1
////        params.addArgument("num2", "2");//设置参数，并赋予默认值2
////        JavaSamplerContext arg0 = new JavaSamplerContext(params);
////        perftest test = new perftest();
////        test.setupTest(arg0);
////        SampleResult sr = test.runTest(arg0);
////        System.out.println(sr.getResponseDataAsString());
////        test.teardownTest(arg0);
////    }
//
//
//}
