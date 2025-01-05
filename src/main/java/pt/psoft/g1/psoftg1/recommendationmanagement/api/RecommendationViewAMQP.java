package pt.psoft.g1.psoftg1.recommendationmanagement.api;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "A Recommendation from AMQP communication")
public class RecommendationViewAMQP {

    @NotNull
    private String lendingNumber;

    @NotNull
    private Long version;

    @NotNull
    private String isbn;

    @NotNull
    private String readerNumber;

    private String commentary;

    private LocalDate returnedDate;

    private Boolean isRecommended;
}
