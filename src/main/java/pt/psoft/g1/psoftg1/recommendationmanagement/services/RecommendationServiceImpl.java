package pt.psoft.g1.psoftg1.recommendationmanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.lendingmanagement.api.LendingViewAMQP;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.recommendationmanagement.api.RecommendationViewAMQP;
import pt.psoft.g1.psoftg1.recommendationmanagement.model.Recommendation;
import pt.psoft.g1.psoftg1.recommendationmanagement.publishers.RecommendationEventsPublisher;
import pt.psoft.g1.psoftg1.recommendationmanagement.repositories.RecommendationRepository;


@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements  RecommendationService {
    private final LendingRepository LendingRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final RecommendationRepository recommendationRepository;

    private final RecommendationEventsPublisher recommendationEventsPublisher;


    @Override
    public Recommendation create(RecommendationViewAMQP recommendationViewAMQP) {
        try {

            final String commentary = recommendationViewAMQP.getCommentary();
            final Boolean isRecommended = recommendationViewAMQP.getIsRecommended();
            final String lendingNumber = recommendationViewAMQP.getLendingNumber();

            final Iterable<Lending> lendings = LendingRepository.findAll();

            LendingRepository.findByLendingNumber(recommendationViewAMQP.getLendingNumber())
                    .orElseThrow(() -> new NotFoundException("Lending not found"));

            final Book book = bookRepository.findByIsbn(recommendationViewAMQP.getIsbn())
                    .orElseThrow(() -> new NotFoundException("Book not found"));

            final ReaderDetails reader =  readerRepository.findByReaderNumber(recommendationViewAMQP.getReaderNumber())
                    .orElseThrow(() -> new NotFoundException("Reader not found"));

            Recommendation recommendation = new Recommendation(book, reader, commentary, isRecommended, lendingNumber);

            Recommendation createdRecommendation = recommendationRepository.save(recommendation);

            if(createdRecommendation == null) {
                throw new Exception("Recommendation not created");
            }

            return recommendation;
        } catch (Exception ex) {
            recommendationEventsPublisher.sendFailedCreatingRecommendation(recommendationViewAMQP);
            System.out.println(" [x] Exception creating recommendation: '" + ex.getMessage() + "'");
            return null;
        }
    }

    @Override
    public void delete(LendingViewAMQP lendingViewAMQP) {
        try {
            final String lendingNumber = lendingViewAMQP.getLendingNumber();
            final Recommendation recommendation = recommendationRepository.findByLendingNumber(lendingNumber)
                    .orElseThrow(() -> new NotFoundException("Recommendation not found"));

            recommendationRepository.delete(recommendation);
        } catch (Exception ex) {
            System.out.println(" [x] Exception deleting recommendation: '" + ex.getMessage() + "'");
        }
    }
}
