package org.ce.cc.core.repository;

import org.ce.cc.core.entity.enumeration.JobState;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class UploadJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public void save(long jobId, long uploadId, String jobQuery, JobState jobState) {
        // TODO correct the error
        Query select = entityManager.
                createNativeQuery("INSERT INTO JOB (JOB_ID,UPLOAD_ID, JOB_QUERY, JOB_STATE ) VALUES(?,?,?,?) ")
                .setParameter(1, uploadId)
                .setParameter(2, jobId)
                .setParameter(3, jobQuery)
                .setParameter(4, jobState.name());
        select.getResultList();
    }
}
