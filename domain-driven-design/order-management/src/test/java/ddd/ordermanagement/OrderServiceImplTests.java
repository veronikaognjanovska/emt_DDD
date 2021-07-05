package ddd.ordermanagement;



import ddd.ordermanagement.domain.exceptions.OrderIdNotExistException;
import ddd.ordermanagement.domain.model.Order;
import ddd.ordermanagement.domain.model.OrderId;
import ddd.ordermanagement.domain.valueObjects.Book;
import ddd.ordermanagement.domain.valueObjects.BookId;
import ddd.ordermanagement.service.OrderService;
import ddd.ordermanagement.service.forms.OrderForm;
import ddd.ordermanagement.service.forms.OrderItemForm;
import ddd.ordermanagement.xport.client.BookClient;
import ddd.sharedkernel.domain.financial.Currency;
import ddd.sharedkernel.domain.financial.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class OrderServiceImplTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BookClient BookClient;



    private static Book newBook(String name, Money price) {
        Book p = new Book(BookId.randomId(BookId.class), name,"00", price, 0);
        return p;
    }

    @Test
    public void testPlaceOrder() {

        OrderItemForm oi1 = new OrderItemForm();
        oi1.setBook(newBook("Pizza",Money.valueOf(Currency.MKD,1500)));
        oi1.setQuantity(1);

        OrderItemForm oi2 = new OrderItemForm();
        oi2.setBook(newBook("Hot Dog",Money.valueOf(Currency.MKD,500)));
        oi2.setQuantity(2);

        OrderForm orderForm = new OrderForm();
        orderForm.setCurrency(Currency.MKD);
        orderForm.setItems(Arrays.asList(oi1,oi2));

        OrderId newOrderId = orderService.placeOrder(orderForm);
        Order newOrder = orderService.findById(newOrderId).orElseThrow(OrderIdNotExistException::new);
        Assertions.assertEquals(newOrder.totalPrice(),Money.valueOf(Currency.MKD,2500));

    }

    @Test
    public void testPlaceOrderWithRealData() {
        List<Book> BookList = BookClient.findAll();
        System.out.println(BookList);
//        Book p1 = BookList.get(0);
//        Book p2 = BookList.get(1);
//
//        OrderItemForm oi1 = new OrderItemForm();
//        oi1.setBook(p1);
//        oi1.setQuantity(1);
//
//        OrderItemForm oi2 = new OrderItemForm();
//        oi2.setBook(p2);
//        oi2.setQuantity(2);
//
//        OrderForm orderForm = new OrderForm();
//        orderForm.setCurrency(Currency.MKD);
//        orderForm.setItems(Arrays.asList(oi1,oi2));
//
//        OrderId newOrderId = orderService.placeOrder(orderForm);
//        Order newOrder = orderService.findById(newOrderId).orElseThrow(OrderIdNotExistException::new);
//
//        Money outMoney = p1.getBookPrice().multiply(oi1.getQuantity()).add(p2.getBookPrice().multiply(oi2.getQuantity()));
//        Assertions.assertEquals(newOrder.totalPrice(),outMoney);
    }


}
