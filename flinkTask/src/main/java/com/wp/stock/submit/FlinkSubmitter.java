//package com.wp.stock.submit;
//
//import org.apache.flink.client.ClientUtils;
//import org.apache.flink.client.RemoteExecutor;
//import org.apache.flink.client.program.ClusterClient;
//import org.apache.flink.client.program.MiniClusterClient;
//import org.apache.flink.client.program.PackagedProgram;
//import org.apache.flink.client.program.ProgramInvocationException;
//import org.apache.flink.configuration.Configuration;
//import org.apache.flink.queryablestate.network.Client;
//import org.apache.flink.yarn.YarnClusterClient;
//import org.apache.flink.yarn.YarnClusterDescriptor;
//import org.apache.hadoop.yarn.client.api.YarnClient;
//import org.apache.hadoop.yarn.conf.YarnConfiguration;
//
//import java.net.InetSocketAddress;
//
//public class FlinkSubmitter {
//
//    private static YarnConfiguration yarnConfiguration;
//
//    private static YarnClient yarnClient;
//
//    static {
//        yarnConfiguration = new YarnConfiguration();
//        yarnClient = YarnClient.createYarnClient();
//        yarnClient.init(yarnConfiguration);
//        yarnClient.start();
//    }
//
//    public static void main(String[] args) {
//        try {
//            PackagedProgram program = new PackagedProgram(file, args);
////            InetSocketAddress jobManagerAddress = RemoteExecutor.getInetFromHostport("localhost:6123");
//            InetSocketAddress jobManagerAddress = ClientUtils.parseHostPortAddress("localhost:6123");
////            InetSocketAddress jobManagerAddress = new InetSocketAddress("master", 6123);
//            Configuration config = new Configuration();
//
//            YarnClusterDescriptor yarnClusterDescriptor = new YarnClusterDescriptor(config, yarnConfiguration, "", yarnClient, false);
//            new YarnClusterClient(yarnClusterDescriptor,1,1,)
//            ClusterClient client = new yarnclusterc(jobManagerAddress, config, program.getUserCodeClassLoader());
//
//            // set the parallelism to 10 here
//            client.run(program, 10, true);
//
//        } catch (ProgramInvocationException e) {
//            e.printStackTrace();
//        }
//    }
//}
