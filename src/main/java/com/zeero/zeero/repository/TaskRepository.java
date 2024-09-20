package com.zeero.zeero.repository;

import com.zeero.zeero.model.Tasks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Tasks, Long> {
    Page<Tasks> findByUserId(Long userId, Pageable pageable);
}
