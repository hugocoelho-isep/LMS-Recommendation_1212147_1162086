package pt.psoft.g1.psoftg1.recommendationmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

@Entity
@Table
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Version
    @Getter
    private long version;

    @NotNull
    @Getter
    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull
    @Getter
    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    @JoinColumn(name = "reader_details_id", nullable = false)
    private ReaderDetails readerDetails;

    @NotNull
    @Getter
    private String lendingNumber;

    @Getter
    private String commentary = null;

    @NotNull
    @Getter
    private Boolean isRecommended;


    public Recommendation(Book book, ReaderDetails readerDetails, String commentary, Boolean isRecommended, String lendingNumber) {
        this.book = book;
        this.readerDetails = readerDetails;
        this.commentary = commentary;
        this.isRecommended = isRecommended;
        this.lendingNumber = lendingNumber;
    }


    /**Protected empty constructor for ORM only.*/
    protected Recommendation() {}
}

