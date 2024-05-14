//package org.example.orderservice.gprcserver;
//
//import io.grpc.Server;
//import io.grpc.ServerBuilder;
//import org.example.orderservice.infrastructure.OrderRepository;
//import org.example.orderservice.service.OrderService;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.Bean;
//
//import java.io.IOException;
//
//
//public class GrpcServer {
//    @Bean
//    public Server grpcServer(OrderService orderService, OrderRepository orderRepository) throws IOException {
//        return ServerBuilder.forPort(8912)
//                .addService(new OrderServiceGrpc(orderService,orderRepository))
//                .build();
//    }
//
//    public static void startGrpcServer(ConfigurableApplicationContext context) throws IOException, InterruptedException {
//        Server grpcServer = context.getBean(Server.class);
//        grpcServer.start();
//    }
//
//}
