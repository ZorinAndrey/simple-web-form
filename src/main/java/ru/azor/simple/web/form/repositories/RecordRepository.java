package ru.azor.simple.web.form.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.azor.simple.web.form.entities.Record;
import ru.azor.simple.web.form.enums.RecordingDirection;

import java.sql.Timestamp;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query("select count(r) from Record r where r.recordingTime = ?1 and r.recordingDirection = ?2")
    Long countRecordingTimeAndRecordDirectionPresent(Timestamp recordingTime, RecordingDirection recordingDirection);
}
