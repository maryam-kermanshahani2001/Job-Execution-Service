package org.ce.cc.core.entity;

import org.ce.cc.core.entity.enumeration.ResultState;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Maryam Kermanshahani
 * @since 07.03.23
 */
@Data
@Entity
@Table(name = "RESULT")
public class Result {

    @Id
    @Column(name = "RESULT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_id_seq")
    @SequenceGenerator(name = "result_id_seq", sequenceName = "result_id_seq", allocationSize = 1)
    private Long resultId;

    @Column(name = "JOB_ID", nullable = false)
    private long jobId;

    @Column(name = "EXEC_OUTPUT")
    private String outPut;

    @Enumerated(EnumType.STRING)
    @Column(name = "RESULT_STATE", nullable = false)
    private ResultState resultState;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXEC_DATE", nullable = false)
    private Date executionDate;
}
