package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

/**
 * Created by jt on 2019-07-17.
 */
@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

//    @Scheduled(fixedRate = 2000)
//    public void sendMessage(){
//
//        System.out.println("I'm Sending a message");
//
//        HelloWorldMessage message = HelloWorldMessage
//                .builder()
//                .id(UUID.randomUUID())
//                .message("Hello World!")
//                .build();
//
//        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
//
//        System.out.println("Message Sent!");
//
//    }
    @Scheduled(fixedRate = 2000)
    public void sendAndReciveMessage(){

        System.out.println("I'm Sending a message");

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        jmsTemplate.sendAndReceive(JmsConfig.MY_QUEUE2, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                try {
                    Message helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));

                    helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");
                    return helloMessage;
                }catch (com.fasterxml.jackson.core.JsonProcessingException jsonProcessingException){
                    throw new javax.jms.JMSException("boom!!!");
                }
            }
        });
        System.out.println("Message Sent!");

    }

}
