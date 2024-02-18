package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.Authority;
import org.springframework.data.repository.Repository;

public interface AuthorityRepository extends Repository<Authority, String> {

    Authority save(Authority authority);

}