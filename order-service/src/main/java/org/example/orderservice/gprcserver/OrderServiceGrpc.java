package org.example.orderservice.gprcserver;

import com.jun.models.OrderObject;
import com.jun.models.OrderRequest;
import com.jun.models.OrderResponse;
import com.jun.models.OrderServiceGrpcGrpc;
import io.grpc.stub.StreamObserver;
import org.example.orderservice.infrastructure.OrderEntity;
import org.example.orderservice.infrastructure.OrderRepository;
import org.example.orderservice.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceGrpc extends OrderServiceGrpcGrpc.OrderServiceGrpcImplBase {

    private OrderRepository orderRepository;
    public OrderServiceGrpc(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    public void getOrdersById(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        String userId = request.getUserId();
        List<OrderEntity> orderEntites = orderRepository.findAllByUserId(userId);
        System.out.println("ORDERENTITY" + orderEntites);
        OrderResponse.Builder responseBuilder = OrderResponse.newBuilder();

        for (OrderEntity orderEntity:orderEntites) {
            OrderObject orderObject = OrderObject.newBuilder()
                    .setId(orderEntity.getId())
                    .setUserId(orderEntity.getUserId())
                    .setQty(orderEntity.getQty())
                    .setUnitPrice(orderEntity.getUnitPrice())
                    .setTotalPrice(orderEntity.getTotalPrice())
                    .build();
            responseBuilder.addOrderObjects(orderObject);
        }



        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }





}
