package org.ce.cc.core.repository;

import org.ce.cc.core.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Maryam Kermanshahani
 * @since 08.03.23
 */
public interface UploadRepository extends JpaRepository<Upload, Long> {

    Upload findByUploadId(long uploadId);
    //    Optional<Upload> findById(Long uploadId);

    List<Upload> getUploadsByEmail(String email);


}
