package com.jungstagram.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jungstagram.domain.Token;

public interface TokenRepository extends JpaRepository<Token, String> {

}
