package com.sougata.domain.domain.labTestTemplate.repository;

import com.sougata.domain.domain.labTestTemplate.entity.LabTestTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestTemplateRepository extends JpaRepository<LabTestTemplateEntity, Long>, JpaSpecificationExecutor<LabTestTemplateEntity> {
    @Query("select e from LabTestTemplateEntity e " +
            "join fetch e.subServices f " +
            "where f.id = ( " +
            "select a.id from SubServiceEntity a " +
            "left join ApplicationEntity b " +
            "on b.subService.id = a.id " +
            "left join DeviceEntity c " +
            "on c.application.id = b.id " +
            "left join JobEntity d " +
            "on d.device.id = c.id " +
            "where d.id = :jobId" +
            " )")
    List<LabTestTemplateEntity> findByJobId(Long jobId);

    @Query("select e from LabTestTemplateEntity e " +
            "join fetch e.subServices f " +
            "where f.id = :subServiceId")
    List<LabTestTemplateEntity> findBySubServiceId(Long subServiceId);
}
