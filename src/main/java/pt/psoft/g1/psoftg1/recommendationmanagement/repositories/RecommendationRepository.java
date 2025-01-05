package pt.psoft.g1.psoftg1.recommendationmanagement.repositories;

import pt.psoft.g1.psoftg1.recommendationmanagement.api.RecommendationViewAMQP;
import pt.psoft.g1.psoftg1.recommendationmanagement.model.Recommendation;

import java.util.Optional;

public interface RecommendationRepository {
    Recommendation save(Recommendation recommendation);
    void delete(Recommendation recommendation);
    Optional<Recommendation> findByLendingNumber(String lendingNumber);
}
