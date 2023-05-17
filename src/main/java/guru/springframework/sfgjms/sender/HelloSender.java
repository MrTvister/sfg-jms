package guru.springframework.sfgjms.sender;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private  final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendHello(){
        System.out.println("Sending Message");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder().id(UUID.randomUUID()).message("Hello").build();
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEVE, helloWorldMessage);
    }
}
