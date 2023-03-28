package org.ce.cc.core.entity;

import org.ce.cc.core.entity.enumeration.JobState;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "JOB")
public class Job {
    @Id
    @Column(name = "JOB_ID")
    @SequenceGenerator(name = "job_id_seq", sequenceName = "job_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_id_seq")
    private long jobId;

    @Column(name = "UPLOAD_ID", nullable = false)
    private long uploadId;

    @Column(name = "JOB_QUERY", nullable = false)
    private String jobQuery;

    // status
    @Enumerated(EnumType.STRING)
    @Column(name = "JOB_STATE", nullable = false)
    private JobState jobState;
}
