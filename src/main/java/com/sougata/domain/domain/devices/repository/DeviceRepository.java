package com.sougata.domain.domain.devices.repository;

import com.sougata.domain.domain.devices.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    @Query("select e from DeviceEntity e where e.application.referenceNumber = :referenceNumber")
    List<DeviceEntity> findByReferenceNumber(String referenceNumber);

    default List<Map<String, Object>> findDeviceWithJobByApplicationReferenceNumber(String referenceNumber, JdbcTemplate jdbcTemplate) {
        String query = """
                select
                    a.name as  device_name,
                    c.id as job_id,
                    d.post_description as status
                from devices a
                left join applications b
                on b.id = a.application_id
                left join jobs c
                on c.device_id = a.id
                left join statuses d
                on d.id = c.status_id
                where b.reference_number = ?
                """;

        return jdbcTemplate.query(query, ps -> ps.setString(1, referenceNumber), (rs, rowNum) -> {
            Map<String, Object> response = new HashMap<>();
            response.put("device_name", rs.getString("device_name"));
            response.put("job_id", rs.getLong("job_id"));
            response.put("status", rs.getString("status"));
            return response;
        });
    }
}
