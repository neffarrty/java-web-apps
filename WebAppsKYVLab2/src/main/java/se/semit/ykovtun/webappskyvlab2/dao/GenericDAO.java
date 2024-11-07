package se.semit.ykovtun.webappskyvlab2.dao;

import java.util.List;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-10-08
 */
public interface GenericDAO<T> {
    T findById(Long id);
    List<T> findByTemplate(T template);
    T findByKey(String key, Object value);
    List<T> findAll();
    void save(T entity);
    void update(Long id, T entity);
    void delete(Long id);
}