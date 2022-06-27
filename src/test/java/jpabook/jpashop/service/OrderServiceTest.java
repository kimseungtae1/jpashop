package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    
    @Test
    public void 주문() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("경제론", 20000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order getOrder = orderRepository.findOne(orderId);

        //then
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(),
                "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(),
                "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(20000 * 2, getOrder.getTotalPrice(),
                "주문 가격은 가격 * 수량이다.");
        assertEquals(8, item.getStockQuantity(),
                "주문 수량만큼 재고가 줄어야 한다.");

    }

    @Test
    public void 주문시_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("경제론", 20000, 10);
        int orderCount = 11;

        //when
        NotEnoughStockException e = assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });

        //then
        assertEquals(e.getMessage(), "재고는 0보다 작을 수 없습니다.");
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("경제론", 20000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        
        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(),
                "주문 취소시 상태는 CANCEL 이다.");
        assertEquals(10, item.getStockQuantity(),
                "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");

    }


    private Member createMember() {
        Member member = new Member();
        member.setUsername("김승태");
        member.setAddress(new Address("서울", "강서로", "22110"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}