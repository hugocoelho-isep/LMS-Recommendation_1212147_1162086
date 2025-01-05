package pt.psoft.g1.psoftg1.recommendationmanagement.infraestructure.publishers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingViewAMQP;
import pt.psoft.g1.psoftg1.recommendationmanagement.api.RecommendationViewAMQP;
import pt.psoft.g1.psoftg1.recommendationmanagement.api.RecommendationViewAMQPMapper;
import pt.psoft.g1.psoftg1.recommendationmanagement.model.Recommendation;
import pt.psoft.g1.psoftg1.recommendationmanagement.publishers.RecommendationEventsPublisher;
import pt.psoft.g1.psoftg1.shared.model.LendingEvents;

@Service
@RequiredArgsConstructor
public class RecommendationEventsRabbitmqPublisherImpl implements RecommendationEventsPublisher {

    @Autowired
    private RabbitTemplate template;
    @Autowired
    @Qualifier("directExchangeLendings")
    private DirectExchange direct;
    private final RecommendationViewAMQPMapper recommendationViewAMQPMapper;

    @Override
    public void sendFailedCreatingRecommendation(RecommendationViewAMQP recommendationViewAMQP) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            String jsonString = objectMapper.writeValueAsString(recommendationViewAMQP);

            this.template.convertAndSend(direct.getName(), LendingEvents.LENDING_RECOMMENDATION_FAILED, jsonString);

            System.out.println(" [x] Sent '" + jsonString + "'");
        }
        catch( Exception ex ) {
            System.out.println(" [x] Exception sending lending with recommendation event: '" + ex.getMessage() + "'");
        }
    }
}
