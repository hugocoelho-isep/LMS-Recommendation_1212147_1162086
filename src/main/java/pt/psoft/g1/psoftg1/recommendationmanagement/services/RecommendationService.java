package pt.psoft.g1.psoftg1.recommendationmanagement.services;

import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingViewAMQP;
import pt.psoft.g1.psoftg1.recommendationmanagement.api.RecommendationViewAMQP;
import pt.psoft.g1.psoftg1.recommendationmanagement.model.Recommendation;

public interface RecommendationService {
    Recommendation create(RecommendationViewAMQP recommendationViewAMQP);

    void delete(LendingViewAMQP lendingViewAMQP);
}
