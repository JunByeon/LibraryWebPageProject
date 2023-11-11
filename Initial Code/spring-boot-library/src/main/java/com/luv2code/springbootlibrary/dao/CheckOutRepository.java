package com.luv2code.springbootlibrary.dao;

import com.luv2code.springbootlibrary.entity.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckOutRepository extends JpaRepository<CheckOut, Long> {
    CheckOut findByUserEmailAndBookId(String userEmail, Long bookId);

    List<CheckOut> findBooksByUserEmail(String userEmail);

    // Delete할때 싹다 지워주기위해 아래와 같이 @Modifying과 @Query("delete from checkout where book_id in :book_id")을 달아줌
    @Modifying
    @Query("delete from checkout where book_id in :book_id")
    void deleteAllByBookId(@Param("book_id") Long bookId);

}
