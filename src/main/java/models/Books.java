package models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Books {

    private List<Book> books;

}