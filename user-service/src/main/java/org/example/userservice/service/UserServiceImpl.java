package org.example.userservice.service;

import com.jun.models.OrderObject;
import com.jun.models.OrderRequest;
import com.jun.models.OrderResponse;
import com.jun.models.OrderServiceGrpcGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.domain.ResponseOrder;
import org.example.userservice.domain.UserDto;
import org.example.userservice.infrastructure.UserEntity;
import org.example.userservice.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    private RestTemplate restTemplate;

    private static OrderServiceGrpcGrpc.OrderServiceGrpcBlockingStub blockingStub;
    public UserServiceImpl(UserRepository userRepository,RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",8912)
                .usePlaintext()
                .build();
        blockingStub = OrderServiceGrpcGrpc.newBlockingStub(managedChannel);
        this.restTemplate = restTemplate;

    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setId(String.valueOf(UUID.randomUUID()));
        UserEntity userEntity = mapper.map(userDto,UserEntity.class);
        UserEntity newUserEntity = userRepository.save(userEntity);
        UserDto newUserDto = mapper.map(newUserEntity,UserDto.class);
        return newUserDto;
    }

    @Override
    public UserDto getUserById(String id) {
        UserEntity userEntity = userRepository.findById(id);
        UserDto userDto = mapper.map(userEntity,UserDto.class);

        OrderRequest orderRequest = OrderRequest.newBuilder().setUserId(userDto.getId()).build();
        OrderResponse orderResponse = blockingStub.getOrdersById(orderRequest);
        List<ResponseOrder> ResponseOrders = new ArrayList<>();

        for (OrderObject orderObject : orderResponse.getOrderObjectsList()) {
            ResponseOrder responseOrder = new ResponseOrder();
            responseOrder.setId(orderObject.getOrderId());
            responseOrder.setQty(orderObject.getQty());
            responseOrder.setUserId(orderObject.getUserId());
            responseOrder.setUnitPrice(orderObject.getUnitPrice());
            responseOrder.setProductId(orderObject.getProductId());
            responseOrder.setTotalPrice(orderObject.getTotalPrice());
            ResponseOrders.add(responseOrder);
        }

        userDto.setOrderList(ResponseOrders);

        return userDto;
    }
    @Override
    public UserDto getUserByIdRest(String id) {
        UserEntity userEntity = userRepository.findById(id);
        UserDto userDto = mapper.map(userEntity,UserDto.class);
        String url = "http://localhost:8082/orders/" + id;
        List<ResponseOrder> responseOrders = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ResponseOrder>>() {
        }).getBody();

        userDto.setOrderList(responseOrders);

        return userDto;
    }

    @Override
    @Transactional
    public List<UserDto> getUserByAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> userDtos = userEntities.stream()
                .map(userEntity -> mapper.map(userEntity,UserDto.class))
                .collect(Collectors.toList());
        return userDtos;
    }
}
