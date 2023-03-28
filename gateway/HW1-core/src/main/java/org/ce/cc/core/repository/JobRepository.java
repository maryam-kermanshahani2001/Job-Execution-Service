package org.ce.cc.core.repository;

import org.ce.cc.core.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Maryam Kermanshahani
 * @since 08.03.23
 */
public interface JobRepository extends JpaRepository<Job, Long> {
    Job findJobByUploadId(long uploadId);

//    List<Job> findJobsByStatusEquals('none-executed');
}
