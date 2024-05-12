package org.example.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.userservice.domain.RequestUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
	@DisplayName("Get user")
	public void getUser() throws  Exception {
		mockMvc.perform(get("/user/testId"))
				.andExpect(status().isOk())
				.andDo(print());
//		assertThat()
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


}
