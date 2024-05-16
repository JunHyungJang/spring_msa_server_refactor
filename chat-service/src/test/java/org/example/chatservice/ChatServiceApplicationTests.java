package org.example.chatservice;

import org.example.chatservice.domain.ChatDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.example.chatservice.service.ChatServiceImpl;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class ChatServiceApplicationTests {


	private final ChatServiceImpl chatService;
	@Autowired
	private WebSocketStompClient webSocketStompClient;
	@Autowired
	public ChatServiceApplicationTests(ChatServiceImpl chatServiceImpl) {
		this.chatService = chatServiceImpl;
	}

	@Test
	void kafkaTest() {
		ChatDto chatDto = ChatDto.builder().roomId(44).name("jun").content("hello world").build();
		chatService.sendMessage(chatDto);
	}

	@Test
	void PartitionTest(){
		for(int i = 0; i < 20; i ++) {
			ChatDto chatDto = ChatDto.builder().roomId(i).name("test").content("Helloworld" + i).build();
			chatService.sendMessage(chatDto);
		}
	}
//	@Test
//	void socketTest() throws InterruptedException, ExecutionException, TimeoutException {
//
//		ChatDto chatDto = ChatDto.builder().roomId(44).name("jun").content("hello world").build();
//		WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
//
//		StompSession stompSession = webSocketStompClient.connect("ws://localhost:9001/chattting", headers, new StompSessionHandlerAdapter() {
//		}).get(1, TimeUnit.SECONDS);
//
//
//		stompSession.subscribe("/sub/chatroom/2", new StompSessionHandlerAdapter() {
//			@Override
//			public Type getPayloadType(StompHeaders headers) {
//				System.out.println(headers);
//				return super.getPayloadType(headers);
//			}
//
//			@Override
//			public void handleFrame(StompHeaders headers, Object payload) {
//				System.out.println(payload);
//				super.handleFrame(headers, payload);
//			}
//		});
//
//		stompSession.send("/pub/chatroom",chatDto);
//
//		Thread.sleep(1000);
//
//	}


}
