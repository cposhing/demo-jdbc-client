package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@SpringBootApplication
public class DemojdbcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemojdbcClientApplication.class, args);
    }

}


@Controller
@ResponseBody
class CustomerController {

    private final CustomerRepository repository;

    CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customers")
    Collection<Customer> getAll() {
        return repository.getAll();
    }

    @GetMapping("/customer/{id}")
    Customer getCustomerById(@PathVariable Long id) {
        return repository.getCustomerById2(id);
    }


    @GetMapping("/orders/{customerId}")
    Collection<Orders> getOrdersByCustomerId(@PathVariable Long customerId) {
        return repository.getOrderById(customerId);
    }

}


@Repository
class CustomerRepository {

    private final JdbcClient jdbcClient;

    CustomerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    Collection<Customer> getAll() {
        return this.jdbcClient
                .sql("select * from customer")
                .query((rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("name"))).list();
    }

    Customer getCustomerById(Long id) {
        return this.jdbcClient
                .sql("select * from customer where id = :id").param("id", id)
                .query((rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("name"))).single();
    }

    Customer getCustomerById2(Long id) {
        return this.jdbcClient
                .sql("select * from customer where id = :id")
                .param("id", id)
                .query(Customer.class).optional().orElseThrow();
    }

    Collection<Orders> getOrderById(Long customerId) {
        return this.jdbcClient
                .sql("select * from orders where customer_id = :customerId")
                .param("customerId", customerId)
                .query(Orders.class).list();
    }
}


record Orders(Long id, Long customerId, String content) {
}

record Customer(Long id, String name) {
}
