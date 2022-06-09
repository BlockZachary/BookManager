package book.manage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {
    int bid;
    String title;
    String des;
    double price;

    public Book(String title, String des, double price) {
        this.title = title;
        this.des = des;
        this.price = price;
    }
}
