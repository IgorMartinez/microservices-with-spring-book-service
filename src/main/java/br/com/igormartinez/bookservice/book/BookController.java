package br.com.igormartinez.bookservice.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;
    
    @GetMapping("/{id}/{currency}")
    public BookResponse findById(
        @PathVariable(name = "id") Long id,
        @PathVariable(name = "currency") String currency
    ) {
        return service.findById(id, currency);
    }
}
