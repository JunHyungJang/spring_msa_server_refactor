package org.example.orderservice;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.orderservice.gprcserver.OrderServiceGrpc;
import org.example.orderservice.infrastructure.OrderRepository;
import org.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;


@SpringBootApplication
public class OrderServiceApplication {


	@Autowired
	private OrderRepository orderRepository;
	public static void main(String[] args) throws IOException, InterruptedException {

		ConfigurableApplicationContext context = SpringApplication.run(OrderServiceApplication.class, args);
		startGrpcServer(context);
	}

	@Bean
//@ConditionalOnBean(name = , value = )
	public Server grpcServer(OrderService orderService) throws IOException {
		return ServerBuilder.forPort(8912)
				.addService(new OrderServiceGrpc(orderRepository))
				.build();
	}

	private static void startGrpcServer(ConfigurableApplicationContext context) throws IOException, InterruptedException {
		Server grpcServer = context.getBean(Server.class);
		grpcServer.start();
	}

}
