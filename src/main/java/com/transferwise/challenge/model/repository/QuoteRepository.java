package com.transferwise.challenge.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transferwise.challenge.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, String> {

}
