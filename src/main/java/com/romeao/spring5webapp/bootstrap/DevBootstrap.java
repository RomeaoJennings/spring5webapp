package com.romeao.spring5webapp.bootstrap;

import com.romeao.spring5webapp.model.Author;
import com.romeao.spring5webapp.model.Book;
import com.romeao.spring5webapp.repositories.AuthorRepository;
import com.romeao.spring5webapp.repositories.BookRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public DevBootstrap(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void generateAuthorAndBook(String fName, String lName, String title, String isbn, String publisher)
    {
        Author author = new Author(fName, lName);
        Book book = new Book(title, isbn, publisher);
        author.getBooks().add(book);
        book.getAuthors().add(author);

        authorRepository.save(author);
        bookRepository.save(book);


    }

    private void initData()
    {
        generateAuthorAndBook("Eric",
                "Evans",
                "Domain Driven Design",
                "1234",
                "Harper Collins"
        );

        generateAuthorAndBook("Rod",
                "Johnson",
                "J2EE Development Without EJB",
                "23444",
                "WORX"
        );
    }
}
