package com.zhuyingcong.orders.dao;

import com.zhuyingcong.orders.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Transactional
    @Query("update Order set status = :newStatus, updateTime = :updateTime" +
            " where id = :id and status = :oldStatus")
    int changeStatus(@Param("id") long id,
                     @Param("oldStatus") int oldStatus,
                     @Param("newStatus") int newStatus,
                     @Param("updateTime") long updateTime);


//    @Query("select id, distance, status from Order limit :limit offset :offset")
//    List<Order> pageQuery(@Param("limit") int limit, @Param("offset") int offset);
}
