package com.sougata.domain.domain.labTestRecord.repository;

import com.sougata.domain.domain.labTestRecord.entity.LabTestRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public interface LabTestRecordRepository extends JpaRepository<LabTestRecordEntity, Long> {
    @Query("select e from LabTestRecordEntity e where e.job.id = :jobId and e.template.id = :templateId")
    Optional<LabTestRecordEntity> findByJobIdAndTemplateId(Long jobId, Long templateId);

    default Map<Long, Object> findTestRecordsCount(Long jobId, JdbcTemplate jdbcTemplate) {
        String sql = """
                select
                    a.template_id,
                    b.name,
                    count(*) as key_count
                from lab_test_records a
                         left join lab_test_templates b
                                   on b.id = a.template_id
                         left join jobs c
                                   on c.id = a.job_id
                         cross join jsonb_object_keys(a.cell_data::jsonb) as k
                where c.id = ?
                group by a.template_id, b.name;
                """;
        Map<Long, Object> res = new HashMap<>();

        jdbcTemplate.query(sql, ps -> ps.setLong(1, jobId), (rs, rowNum) -> {
            Map<String, Object> response = new HashMap<>();
            response.put("keyCount", rs.getLong("key_count"));
            response.put("templateId", rs.getLong("template_id"));
            response.put("templateName", rs.getString("name"));
            return response;
        }).forEach(r -> res.put((Long) r.get("templateId"), r.get("keyCount")));
        return res;
    }
}
