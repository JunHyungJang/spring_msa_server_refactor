package org.example.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.models.OrderObject;
import com.jun.models.OrderRequest;
import com.jun.models.OrderResponse;
import com.jun.models.OrderServiceGrpcGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.orderservice.domain.OrderDto;
import org.example.orderservice.domain.RequestOrder;
import org.example.orderservice.gprcserver.OrderServiceGrpc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static OrderServiceGrpcGrpc.OrderServiceGrpcBlockingStub blockingStub;
	private static OrderServiceGrpcGrpc.OrderServiceGrpcStub stub;
	@BeforeAll
	public static void setup() {
		ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",8912)
				.usePlaintext()
				.build();
		blockingStub = OrderServiceGrpcGrpc.newBlockingStub(managedChannel);
		stub = OrderServiceGrpcGrpc.newStub(managedChannel);
		//		this.blockingStub = com.jun.models.OrderServiceGrpcGrpc.newBlockingStub(managedChannel);
//		this.OrderServiceStub = com.jun.models.OrderServiceGrpcGrpc.newStub(managedChannel);
	}
	@Test
	@DisplayName("Health check")
	public void healthCheck() throws Exception {
		mockMvc.perform(get("/health_check"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@DisplayName("Create Order")
	public void createOrders() throws Exception {

		RequestOrder requestOrder = RequestOrder.builder()
				.userId("testId")
				.productId("car")
				.unitPrice(1000)
				.qty(3)
				.build();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/order")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestOrder)))
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();

		OrderDto orderDto = objectMapper.readValue(responseBody,OrderDto.class);

		assertThat(orderDto.getUserId()).isEqualTo("testId");
	}
	@Test
	@DisplayName("Get All Orders")
	public void getAllOrders() throws Exception {
		mockMvc.perform(get("/orders"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@DisplayName("Get All Orders By UserId")
	public void getAllOrdersByUserId() throws Exception {
		mockMvc.perform(get("/orders/testId"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@DisplayName("GRPC TEST")
	public void getAllOrdersByUserdIdGprc(){
		OrderRequest orderRequest = OrderRequest.newBuilder().setUserId("testId").build();
		OrderResponse orderResponse = blockingStub.getOrdersById(orderRequest);

		System.out.println(orderResponse);
		OrderObject orderObject = orderResponse.getOrderObjectsList().get(0);
//		assertThat(orderObject.getUserId()).isEqualTo("testId");
//		assertThat(orderObject.get
//		assertThat(orderResponse.getOrderObjects())
	}



}
