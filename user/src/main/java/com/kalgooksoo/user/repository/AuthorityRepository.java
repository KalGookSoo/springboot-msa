package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.entity.Authority;

import java.util.List;

/**
 * 권한 저장소
 */
public interface AuthorityRepository  {

    Authority save(Authority authority);

    List<Authority> findByUserId(String userId);

    void deleteByUserId(String userId);

}