package org.ce.cc.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import org.ce.cc.core.entity.Job;
import org.ce.cc.core.entity.Result;
import org.ce.cc.core.entity.Upload;
import org.ce.cc.core.entity.enumeration.Language;
import org.ce.cc.core.repository.JobRepository;
import org.ce.cc.core.repository.ResultRepository;
import org.ce.cc.core.repository.UploadRepository;
import org.ce.cc.gateway.service.DatabaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {
    private final UploadRepository uploadRepo;
    private final ResultRepository resultRepository;
    private final JobRepository jobRepository;

    @Override
    public Upload getUploadDatabaseById(long uploadId) {

//        Optional<Upload> uploadData = uploadRepo.findById(uploadId);
        return uploadRepo.findByUploadId(uploadId);
    }

    @Override
    public List<Result> getResultsByEmail(String email) {
        List<Upload> uploads = uploadRepo.getUploadsByEmail(email);
        if (uploads.size() == 0)
            return null;
        ArrayList<Result> results = new ArrayList<>();
        for (Upload up : uploads) {
            Job job = jobRepository.findJobByUploadId(up.getUploadId());
            Result res = resultRepository.findResultByJobId(job.getJobId());
            results.add(res);
        }
        return results;
    }

    @Override
    public Upload uploadInfoToDb(String email, String input, Language language, int enable) {
        Upload upload = new Upload();
        upload.setEmail(email);
        upload.setInput(input);
        upload.setLanguage(language);
        upload.setEnable(enable);
        upload = uploadRepo.save(upload);
//        return "database uploaded" + email;
//        if (Objects.isNull(upload.))
//        if (Objects.isNull(upload.findUploadByUploadId())) {
//            throw new AlertEngineInternalException("Cant Save User");
//        }
//        return true;
        return upload;
    }


}