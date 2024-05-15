package org.example.chatservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.example.chatservice.service.ChatServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class ChatServiceApplicationTests {


	private final ChatServiceImpl chatService;
	@Autowired
	public ChatServiceApplicationTests(ChatServiceImpl chatServiceImpl) {
		this.chatService = chatServiceImpl;
	}

	@Test
	void kafkaTest() {
		chatService.test();
	}


}
