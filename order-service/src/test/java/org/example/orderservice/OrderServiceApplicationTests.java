package org.example.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.orderservice.domain.OrderDto;
import org.example.orderservice.domain.RequestOrder;
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




}
