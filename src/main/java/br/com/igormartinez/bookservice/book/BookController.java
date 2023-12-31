package br.com.igormartinez.bookservice.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("/book")
public class BookController {

    private Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookService service;
    
    @Operation(summary = "Find a specific book by your ID")
    @GetMapping("/{id}/{currency}")
    public BookResponse findById(
        @PathVariable(name = "id") Long id,
        @PathVariable(name = "currency") String currency
    ) {
        return service.findById(id, currency);
    }

    @Operation(summary = "Foo Bar")
    @GetMapping("/foo-bar")
    @Retry(name = "foo-bar", fallbackMethod = "fooBarFallback")
    //@CircuitBreaker(name = "default", fallbackMethod = "fooBarFallback")
    //@RateLimiter(name = "foo-bar")
    //@Bulkhead(name = "foo-bar")
    public String fooBar() {
        logger.info("Request to foo-bar is received!");
        return service.fooBar();
    }

    public String fooBarFallback(Exception ex) {
        return "Fallback foo-bar";
    }
}
