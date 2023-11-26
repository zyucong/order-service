package com.zhuyingcong.orders.dao;

import com.zhuyingcong.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Transactional
    @Query("update Order set status = :newStatus, updateTime = :updateTime" +
            " where id = :id and status = :oldStatus")
    int changeStatus(@Param("id") long id,
                     @Param("oldStatus") int oldStatus,
                     @Param("newStatus") int newStatus,
                     @Param("updateTime") long updateTime);

}
