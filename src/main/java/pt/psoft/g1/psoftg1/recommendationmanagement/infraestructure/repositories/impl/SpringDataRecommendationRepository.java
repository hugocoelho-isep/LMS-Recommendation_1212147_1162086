package pt.psoft.g1.psoftg1.recommendationmanagement.infraestructure.repositories.impl;

import org.springframework.data.repository.CrudRepository;


import pt.psoft.g1.psoftg1.recommendationmanagement.model.Recommendation;
import pt.psoft.g1.psoftg1.recommendationmanagement.repositories.RecommendationRepository;

public interface SpringDataRecommendationRepository extends RecommendationRepository, CrudRepository<Recommendation, Long> {
}
