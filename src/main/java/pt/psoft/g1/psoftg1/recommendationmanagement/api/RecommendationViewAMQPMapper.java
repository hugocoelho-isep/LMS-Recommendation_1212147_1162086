package pt.psoft.g1.psoftg1.recommendationmanagement.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.psoft.g1.psoftg1.recommendationmanagement.model.Recommendation;
import pt.psoft.g1.psoftg1.shared.api.MapperInterface;

@Mapper(componentModel = "spring")
public abstract class RecommendationViewAMQPMapper extends MapperInterface {
    @Mapping(target = "lendingNumber", source = "lendingNumber")
    @Mapping(target = "version", source = "version")
    @Mapping(target = "commentary", source = "commentary")
    @Mapping(target = "isRecommended", source = "isRecommended")

    public abstract RecommendationViewAMQP toRecommendationViewAMQP(Recommendation recommendation);
}
