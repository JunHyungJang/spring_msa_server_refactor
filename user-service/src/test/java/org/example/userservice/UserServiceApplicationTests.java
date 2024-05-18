package org.example.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.models.OrderObject;
import org.example.userservice.domain.RequestUser;
import org.example.userservice.domain.ResponseOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Sql(value = "/sql/user-insertion.sql")
class UserServiceApplicationTests {


	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();


//	@BeforeAll

	@Test
	@DisplayName("Health check")
	public void healthCheck() throws Exception {
		mockMvc.perform(get("/health_check"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@DisplayName("Get user GRPC")
	public void getUser() throws  Exception {
		long startTime = System.currentTimeMillis();
		mockMvc.perform(get("/user/testId"))
				.andExpect(status().isOk())
				.andDo(print());
//		assertThat()
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;

		System.out.println("Test duration: " + duration + " ms");
	}

	@Test
	@DisplayName("Get User REST")
	public void getUserRest() throws Exception {
		long startTime = System.currentTimeMillis();
		mockMvc.perform(get("/user/rest/testId"))
				.andExpect(status().isOk())
				.andDo(print());
//		assertThat()
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;

		System.out.println("Test duration: " + duration + " ms");

	}
	@Test
	@DisplayName("Get all users")
	public void getUsers() throws  Exception {
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andDo(print());
	}
	@Test
	@DisplayName("Register")
	public void register() throws  Exception {

		RequestUser requestUser = RequestUser
				.builder()
				.email("jun@naver.com")
				.name("jun")
				.password("junpassword")
				.build();



		mockMvc.perform(MockMvcRequestBuilders.post("/user/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestUser)))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Compare gRPC and REST")
	public void compare() throws IOException {
		OrderObject orderObject = OrderObject.newBuilder()
				.setOrderId("testOrderId")
				.setQty(1)
				.setTotalPrice(1000)
				.setUnitPrice(1000)
				.build();

		ResponseOrder responseOrder = new ResponseOrder();
		responseOrder.setId("testId");
		responseOrder.setQty(1);
		responseOrder.setUnitPrice(1000);
		responseOrder.setTotalPrice(1000);

		Runnable json = () -> {
			try {
				byte[] bytes = objectMapper.writeValueAsBytes(responseOrder);
				ResponseOrder newResponseOrder = objectMapper.readValue(bytes,ResponseOrder.class);
			}catch (Exception e){
				e.printStackTrace();
			}
		};

		Runnable proto = () -> {
			try{
				byte[] bytes = orderObject.toByteArray();
				OrderObject newOrderObject = OrderObject.parseFrom(bytes);

			}catch (Exception e){
				e.printStackTrace();
			}
		};

		runPerformanceTest(json,"JSON");
		runPerformanceTest(proto,"proto");



	}

	private static void runPerformanceTest(Runnable runnable,String method){
		long time1 = System.currentTimeMillis();
		for (int i = 0; i < 2000; i++) {
			runnable.run();
		}

		long time2 = System.currentTimeMillis();

		System.out.println(method + ":"  + (time2-time1) + "ms");
	}

}
