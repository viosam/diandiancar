package com.diandiancar.demo.converter;

import com.diandiancar.demo.dto.BookDTO;
import com.diandiancar.demo.entity.Book;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Book2BookDTOConverter {

    public static BookDTO convert(Book book){

        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book,bookDTO);

        return bookDTO;
    }

    public static List<BookDTO> convert(List<Book> bookList){

        return bookList.stream().map(e->convert(e)).collect(Collectors.toList());
    }

}
