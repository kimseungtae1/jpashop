package jpabook.jpashop.web;

import lombok.Getter;

@Getter
public class UpdateItemDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    protected UpdateItemDto (BookForm form) {
        this.id = form.getId();
        this.name = form.getName();
        this.price = form.getPrice();
        this.stockQuantity = form.getStockQuantity();
        this.author = form.getAuthor();
        this.isbn = form.getIsbn();
    }

}
