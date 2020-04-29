//package com.experto.transaction.services;
//
//import javax.persistence.LockModeType;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Lock;
//import org.springframework.data.jpa.repository.Query;
//
//import org.springframework.data.repository.query.Param;
//
//public interface ArticleRepository extends JpaRepository<Article, Long> {
//
//  @Lock(LockModeType.PESSIMISTIC_WRITE)
//  @Query("select a from Article a where a.id = :id")
//  Article findArticleForWrite(@Param("id") Long id);
//
//  @Lock(LockModeType.PESSIMISTIC_READ)
//  @Query("select a from Article a where a.id = :id")
//  Article findArticleForRead(@Param("id") Long id);
//}
