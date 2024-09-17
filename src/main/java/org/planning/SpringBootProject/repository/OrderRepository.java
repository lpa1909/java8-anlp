package org.planning.SpringBootProject.repository;

import org.planning.SpringBootProject.entity.Order;
import org.planning.SpringBootProject.model.OrderInfo;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query(value = "Select max(o.orderNum) from Order o")
    int getOrderMaxNum();

    @Query("SELECT new org.planning.SpringBootProject.model.OrderInfo(o.id, o.orderDate, o.orderNum, o.amount, o.customerName, o.customerAddress, o.customerEmail, o.customerPhone) FROM Order o ORDER BY o.orderNum DESC")
    Page<OrderInfo> findOrderInfo(Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.userId = :userId")
    Page<Order> findByUserId(@Param("userId") String userId, Pageable pageable);

    @Query(value = "SELECT new org.planning.SpringBootProject.model.OrderInfo(ord.id, ord.orderDate, ord.orderNum, ord.amount,  ord.customerName, ord.customerAddress, ord.customerEmail, ord.customerPhone) from  Order ord WHERE ord.userId = :userId order by ord.orderNum desc")
    org.hibernate.query.Query<OrderInfo> listOrderByUser(@Param("userId") String userId);
}
