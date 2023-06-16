package com.beecoders.ras.repository;

import com.beecoders.ras.model.entity.Promocode;
import com.beecoders.ras.model.response.PromocodeStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromocodeRepository extends JpaRepository<Promocode, Long> {
    Optional<Promocode> findByCode(String code);

    @Query("SELECT new com.beecoders.ras.model.response.PromocodeStatistic(p.holderName, COALESCE(subquery.orderCount, 0L)) " +
            "FROM Promocode p " +
            "LEFT JOIN (" +
            "   SELECT o.promocode.holderName AS holderName, COUNT(o.id) AS orderCount " +
            "   FROM Order o " +
            "   WHERE o.createdAt BETWEEN :from AND :to " +
            "   GROUP BY o.promocode.holderName" +
            ") AS subquery ON p.holderName = subquery.holderName group by p.holderName " +
            "ORDER BY COALESCE(subquery.orderCount, 0L) DESC")
    Page<PromocodeStatistic> retrieveStatisticByDateRange(Timestamp from, Timestamp to, PageRequest pageRequest);
}
