package com.sougata.domain.domain.forms.repository;

import com.sougata.domain.domain.forms.entity.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<FormEntity, Long> {
}
