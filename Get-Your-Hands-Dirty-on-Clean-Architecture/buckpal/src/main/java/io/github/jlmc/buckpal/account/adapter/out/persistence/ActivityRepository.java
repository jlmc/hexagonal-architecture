package io.github.jlmc.buckpal.account.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface ActivityRepository extends JpaRepository<ActivityJpaEntity, Long> {

    @Query(value = "select a from ActivityJpaEntity a where a.ownerAccountId = :ownerAccountId and and a.timestamp >= :since")
    List<ActivityJpaEntity> findByOwnerSince(@Param("ownerAccountId") String ownerAccountId, @Param("since") Instant baselineDate);


    @Query("select sum(a.amount) from ActivityJpaEntity a " +
            "where a.targetAccountId = :accountId " +
            "and a.ownerAccountId = :accountId " +
            "and a.timestamp < :until")
    BigDecimal getDepositBalanceUntil(
            @Param("accountId") String accountId,
            @Param("until") Instant until);

    @Query("select sum(a.amount) from ActivityJpaEntity a " +
            "where a.sourceAccountId = :accountId " +
            "and a.ownerAccountId = :accountId " +
            "and a.timestamp < :until")
    BigDecimal getWithdrawalBalanceUntil(
            @Param("accountId") String accountId,
            @Param("until") Instant until);
}
