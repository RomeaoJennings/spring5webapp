package com.romeao.spring5webapp.bootstrap;

import com.romeao.spring5webapp.model.Author;
import com.romeao.spring5webapp.model.Book;
import com.romeao.spring5webapp.model.Publisher;
import com.romeao.spring5webapp.repositories.AuthorRepository;
import com.romeao.spring5webapp.repositories.BookRepository;
import com.romeao.spring5webapp.repositories.PublisherRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;

    public DevBootstrap(BookRepository bookRepository,
                        AuthorRepository authorRepository,
                        PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void generateAuthorAndBook(String firstName,
                                       String lastName,
                                       String title,
                                       String isbn,
                                       Publisher publisher)
    {
        Author author = new Author(firstName, lastName);
        Book book = new Book(title, isbn, publisher);
        author.getBooks().add(book);
        book.getAuthors().add(author);

        publisherRepository.save(publisher);
        authorRepository.save(author);
        bookRepository.save(book);
    }

    private void initData()
    {
        Publisher harper = new Publisher("Harper Collins", "1 Collins Way");
        generateAuthorAndBook("Eric",
                "Evans",
                "Domain Driven Design",
                "1234",
                harper
        );

        Publisher worx = new Publisher("WORX", "1 Worx Way");
        generateAuthorAndBook("Rod",
                "Johnson",
                "J2EE Development Without EJB",
                "23444",
                worx
        );
    }
}
