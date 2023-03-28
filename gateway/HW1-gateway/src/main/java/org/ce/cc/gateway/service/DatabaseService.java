package org.ce.cc.gateway.service;

import org.ce.cc.core.entity.Result;
import org.ce.cc.core.entity.Upload;
import org.ce.cc.core.entity.enumeration.Language;

import java.util.List;

public interface DatabaseService {
    Upload getUploadDatabaseById (long uploadId);
//    Result getResultsByEmail();
//    List<Upload>
    List<Result> getResultsByEmail(String email);

    Upload uploadInfoToDb( String email, String input, Language language, int enable);


//    List<Upload>
}
