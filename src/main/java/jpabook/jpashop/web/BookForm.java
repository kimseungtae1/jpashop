package jpabook.jpashop.web;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
//@Setter
public class BookForm {

    private Long id;

    @NotEmpty(message = "상품명은 필수 입니다.")
    private String name;
    
    @NotNull(message = "가격은 필수 입니다.")
    //@DecimalMin(value = "0", inclusive = false, message = "가격은 0보다 커야합니다.")
    @Positive(message = "가격은 0보다 커야합니다.")
    private int price;

    @NotNull(message = "수량은 필수 입니다.")
    //@DecimalMin(value = "0", inclusive = false, message = "수량은 0보다 커야합니다.")
    @Positive(message = "수량은 0보다 커야합니다.")
    private int stockQuantity;
    
    private String author;

    @ISBN
    private String isbn;

    protected BookForm(){

    }

    public BookForm(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
