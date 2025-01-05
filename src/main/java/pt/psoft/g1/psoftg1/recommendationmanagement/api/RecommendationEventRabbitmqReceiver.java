package pt.psoft.g1.psoftg1.recommendationmanagement.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingViewAMQP;
import pt.psoft.g1.psoftg1.recommendationmanagement.services.RecommendationService;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class RecommendationEventRabbitmqReceiver {

    private final RecommendationService recommendationService;


    @RabbitListener(queues = "#{autoDeleteQueue_Lending_With_Recommendation.name}")
    public void receiveLendingRecommendationCreated(Message msg) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);
            RecommendationViewAMQP recommendationViewAMQP = objectMapper.readValue(jsonReceived, RecommendationViewAMQP.class);

            System.out.println(" [x] Received Recommendation Created by AMQP: " + msg + ".");
            try {
                recommendationService.create(recommendationViewAMQP);
                System.out.println(" [x] New recommendation inserted from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Recommendation already exists. No need to store it.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving recommendation event from AMQP: '" + ex.getMessage() + "'");
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue_Lending_Recommendation_Failed.name}")
    public void receiveLendingRecommendationFailed(Message msg) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);
            LendingViewAMQP lendingViewAMQP = objectMapper.readValue(jsonReceived, LendingViewAMQP.class);

            System.out.println(" [x] Received Recommendation failed to create by AMQP: " + msg + ". Rollback lending.");
            try {
                recommendationService.delete(lendingViewAMQP);
                System.out.println(" [x] Lending rollback from AMQP: " + msg + ".");
            } catch (Exception e) {
                System.out.println(" [x] Lending does not exists or wrong version. Nothing changed.");
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving lending rollback event from AMQP: '" + ex.getMessage() + "'");
        }
    }

}
