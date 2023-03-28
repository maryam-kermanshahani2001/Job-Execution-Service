package org.ce.cc.core.repository;

import org.ce.cc.core.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Maryam Kermanshahani
 * @since 08.03.23
 */
public interface ResultRepository extends JpaRepository<Result, Long> {
    Result findResultByJobId(long jobId);

}
