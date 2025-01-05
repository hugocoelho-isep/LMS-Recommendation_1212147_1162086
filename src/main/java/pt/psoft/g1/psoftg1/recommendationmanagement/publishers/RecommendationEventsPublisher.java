package pt.psoft.g1.psoftg1.recommendationmanagement.publishers;

import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.recommendationmanagement.api.RecommendationViewAMQP;
import pt.psoft.g1.psoftg1.recommendationmanagement.model.Recommendation;

public interface RecommendationEventsPublisher {
    void sendFailedCreatingRecommendation(RecommendationViewAMQP recommendationViewAMQP);
}
