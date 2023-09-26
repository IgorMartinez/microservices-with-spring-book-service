package br.com.igormartinez.bookservice.book;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.com.igormartinez.bookservice.cambio.CambioProxy;
import br.com.igormartinez.bookservice.cambio.CambioResponse;

@Service
public class BookService {
    private final Environment environment;
    private final BookRepository repository;
    private final CambioProxy cambioProxy;

    public BookService(Environment environment, BookRepository repository, CambioProxy cambioProxy) {
        this.environment = environment;
        this.repository = repository;
        this.cambioProxy = cambioProxy;
    }

    public BookResponse findById(Long id, String currency) {

        Book book = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!currency.equals("USD")) {
            CambioResponse cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);

            book.setPrice(cambio.convertedValue());
        }

        return new BookResponse(
            book.getId(), 
            book.getAuthor(), 
            book.getLaunchDate(), 
            book.getPrice(), 
            book.getTitle(), 
            currency, 
            environment.getProperty("local.server.port"));
    }
}
