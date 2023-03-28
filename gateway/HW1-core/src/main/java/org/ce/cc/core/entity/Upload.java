package org.ce.cc.core.entity;

import lombok.Data;
import org.ce.cc.core.entity.enumeration.Language;

import javax.persistence.*;

@Data
@Entity
@Table(name = "UPLOAD")
public class Upload {

    @Id
    @Column(name = "UPLOAD_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "upload_id_seq")
    @SequenceGenerator(name = "upload_id_seq", sequenceName = "upload_id_seq", allocationSize = 1)
    private long uploadId;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    // TODO: 07.03.23 add Job

    @Column(name = "INPUT", nullable = false)
    private String input;
    @Enumerated(EnumType.STRING)
    @Column(name = "LANGUAGE", nullable = false)
    private Language language;
    @Column(name = "ENABLE", nullable = false)
    private int enable;

//    @Lob
//    @Column(name = "FILE", nullable = false)
//    private MultipartFile file;
}
