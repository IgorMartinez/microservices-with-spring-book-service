package br.com.igormartinez.bookservice.book;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

        String responseEnvoriment = "Book port:" + environment.getProperty("local.server.port");

        if (!currency.equals("USD")) {
            CambioResponse cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);

            book.setPrice(cambio.convertedValue());
            responseEnvoriment += "|Cambio port: " + cambio.enviroment();
        }

        return new BookResponse(
            book.getId(), 
            book.getAuthor(), 
            book.getLaunchDate(), 
            book.getPrice(), 
            book.getTitle(), 
            currency, 
            responseEnvoriment);
    }

    public String fooBar() {
        var response = new RestTemplate()
            .getForEntity("http://localhost:8080/foo-bar", String.class);
        return response.getBody();
    }
}
