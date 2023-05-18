package guru.springframework.sfgjms.reciver;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

@Component
@lombok.AllArgsConstructor
public class HelloListener {
    private  final org.springframework.jms.core.JmsTemplate jmsTemplate;

    @JmsListener(destination = guru.springframework.sfgjms.config.JmsConfig.MY_QUEUE2)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message message) throws JMSException {
//        System.out.println("I get a Message!!!!");
//        System.out.println(helloWorldMessage);
        HelloWorldMessage m = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("World!")
                .build();
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), m);
    }
}
