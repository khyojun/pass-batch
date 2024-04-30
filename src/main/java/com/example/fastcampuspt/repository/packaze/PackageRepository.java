package com.example.fastcampuspt.repository.packaze;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PackageRepository extends JpaRepository<PackageEntity, Integer> {

    List<PackageEntity> findByCreatedAtAfter(LocalDateTime dateTime, Pageable packageSeq);


    @Transactional
    @Modifying // query 어노테이션 중 update,insert,delete 사용하게 될 경우 @modifying 붙이라곻 강제하게됨. 주의점 출처 : https://joojimin.tistory.com/71 참고
    //query로 정의된 벌크 연산 jpql의 경우 기존 jpa처럼 영.컨을 거치는게 아니라 바로 db에 질의하게 됨.
    @Query(value = "Update PackageEntity p Set p.count = :count, p.period=:period where p.packageSeq=:packageSeq") // 벌크 연산처럼 될 수도
    int updateCountAndPeriod(Integer packageSeq, Integer count, Integer period);
}
