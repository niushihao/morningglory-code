package com.morningglory.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.morningglory.model.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianniu
 * @date 2023/3/13
 * @desc
 */
@Component
public class BookService implements GraphQLQueryResolver {

    private final List<Book> books = new ArrayList<>();

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(String id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }

}
