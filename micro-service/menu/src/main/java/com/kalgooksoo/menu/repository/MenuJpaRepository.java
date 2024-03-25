package com.kalgooksoo.menu.repository;

import com.kalgooksoo.menu.domain.Menu;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class MenuJpaRepository implements MenuRepository {

    private final EntityManager em;

    @Override
    public Menu save(@Nonnull Menu menu) {
        try {
            em.persist(menu);
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            em.merge(menu);
        }
        return menu;
    }

    @Override
    public List<Menu> findAll() {
        return em.createQuery("select menu from Menu menu", Menu.class).getResultList();
    }

    @Override
    public Optional<Menu> findById(@Nonnull String id) {
        return Optional.ofNullable(em.find(Menu.class, id));
    }

    @Override
    public void deleteById(@Nonnull String id) {
        Menu menu = em.find(Menu.class, id);
        if (menu != null) {
            em.remove(menu);
        } else {
            throw new NoSuchElementException("메뉴를 찾을 수 없습니다.");
        }
    }
}