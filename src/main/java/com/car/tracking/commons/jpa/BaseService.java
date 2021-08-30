package com.car.tracking.commons.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * @author Nyabinde Nyasha
 * @created 12/18/2020
 * @project boilerplate
 */

public interface BaseService<T, C, U> {

    T findById(Long id);

    void delete(Long id);

    Page<T> findAll(Pageable pageable, String searchQuery);

    T create(C createDto);

    T update(U updateDto);

    Collection<T> findAll();

}
