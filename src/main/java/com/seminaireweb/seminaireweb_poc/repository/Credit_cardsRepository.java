package com.seminaireweb.seminaireweb_poc.repository;

import com.seminaireweb.seminaireweb_poc.entity.Credit_cards;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Credit_cardsRepository extends JpaRepository<Credit_cards, Long> {
    Credit_cards findByNumbers(String card_id);
}
