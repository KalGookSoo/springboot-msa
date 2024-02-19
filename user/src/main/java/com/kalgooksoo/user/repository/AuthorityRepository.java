package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.Authority;

public interface AuthorityRepository  {

    Authority save(Authority authority);

    Iterable<Authority> findByUserId(String userId);

    void deleteByUserId(String userId);

}