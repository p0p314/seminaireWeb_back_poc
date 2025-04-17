package com.seminaireweb.seminaireweb_poc.service;

import com.seminaireweb.seminaireweb_poc.entity.Credit_cards;
import com.seminaireweb.seminaireweb_poc.repository.Credit_cardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Credit_cardsService {

    @Autowired
    private Credit_cardsRepository creditCardsRepository;

    public Credit_cards getCreditCardByNumbers(String creditCards_id) {
        return creditCardsRepository.findByNumbers(creditCards_id);
    }
}
