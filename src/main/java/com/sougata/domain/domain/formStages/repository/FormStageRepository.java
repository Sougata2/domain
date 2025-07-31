package com.sougata.domain.domain.formStages.repository;

import com.sougata.domain.domain.formStages.entity.FormStageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormStageRepository extends JpaRepository<FormStageEntity, Long> {
    @Query("select e from FormStageEntity e " +
            "where e.form.id = :formId " +
            "order by e.stageOrder")
    List<FormStageEntity> findByFormId(Long formId);

    @Query("select f from ApplicationEntity e " +
            "left join FormStageEntity f " +
            "on e.subService.form.id = f.form.id " +
            "where e.referenceNumber = :referenceNumber")
    List<FormStageEntity> findByReferenceNumber(String referenceNumber);
}