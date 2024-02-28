package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.entity.User;
import com.kalgooksoo.user.search.UserSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserJpaRepository implements UserRepository {

    private final EntityManager em;

    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        if (user.getId() == null) {
            em.persist(user);
        } else {
            user = em.merge(user);
        }
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        Assert.notNull(id, "id must not be null");
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Assert.notNull(username, "username must not be null");
        return em.createQuery("select user from User user where user.username = :username", User.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public void deleteById(String id) {
        Assert.notNull(id, "id must not be null");
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        } else {
            throw new NoSuchElementException("계정을 찾을 수 없습니다.");
        }
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        int totalRows = ((Number) em.createQuery("select count(user) from User user").getSingleResult()).intValue();
        List<User> users = em.createQuery("select user from User user", User.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(users, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), totalRows);
    }

    @Override
    public boolean existsByUsername(String username) {
        Assert.notNull(username, "username must not be null");
        Long count = em.createQuery("select count(user) from User user where user.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    /**
     * 검색 조건에 기반한 계정 목록 조회
     *
     * @param search   검색 조건
     * @param pageable 페이지네이션 정보
     * @return 계정 목록
     */
    @Override
    public Page<User> search(UserSearch search, Pageable pageable) {
        String jpql = "select user from User user where 1=1";
        jpql += generateJpql(search);

        TypedQuery<User> query = em.createQuery(jpql, User.class);
        setParameters(query, search);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<User> users = query.getResultList();

        String countJpql = "select count(user) from User user where 1=1";
        countJpql += generateJpql(search);

        TypedQuery<Long> countQuery = em.createQuery(countJpql, Long.class);
        setParameters(countQuery, search);

        Long count = countQuery.getSingleResult();

        return new PageImpl<>(users, pageable, count);
    }

    private String generateJpql(UserSearch search) {
        StringBuilder jpql = new StringBuilder();
        if (search.isEmptyUsername()) {
            jpql.append(" and user.username like :username");
        }
        if (search.isEmptyName()) {
            jpql.append(" and user.name like :name");
        }
        if (search.isEmptyEmailId()) {
            jpql.append(" and user.emailId like :emailId");
        }
        if (search.isEmptyContactNumber()) {
            jpql.append(" and user.contactNumber like :contactNumber");
        }
        return jpql.toString();
    }

    private void setParameters(TypedQuery<?> query, UserSearch search) {
        if (search.isEmptyUsername()) {
            query.setParameter("username", "%" + search.getUsername() + "%");
        }
        if (search.isEmptyName()) {
            query.setParameter("name", "%" + search.getName() + "%");
        }
        if (search.isEmptyEmailId()) {
            query.setParameter("emailId", "%" + search.getEmailId() + "%");
        }
        if (search.isEmptyContactNumber()) {
            query.setParameter("contactNumber", "%" + search.getContactNumber() + "%");
        }
    }

}