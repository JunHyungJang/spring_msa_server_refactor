package org.example.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.productservice.domain.ProductDto;
import org.example.productservice.domain.RequestProduct;
import org.example.productservice.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Sql(value = "/sql/product-insertion.sql")
class ProductServiceApplicationTests {


	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private ProductService productService;

//	public ProductServiceApplicationTests(ProductService productService) {
//		this.productService = productService;
//	}

	@Test
	@DisplayName("update")
	public void Update() {
		RequestProduct requestProduct = RequestProduct.builder().id("car").qty(10).build();
		ProductDto productDto = productService.updateProductStock("car",10);
		System.out.println(productDto);
	}

	@Test
	@DisplayName("Health check")
	public void healthCheck() throws Exception {
		mockMvc.perform(get("/health_check"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@DisplayName("Update Product")
	public void updateProduct() throws Exception {
		RequestProduct requestProduct = RequestProduct.builder().id("car").qty(10).build();
		MvcResult result = mockMvc.perform(put("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestProduct)))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		System.out.println(result);
	}
}
