package org.ce.cc.notifier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.ce.cc.core.entity.Job;
import org.ce.cc.core.entity.Result;
import org.ce.cc.core.entity.Upload;
import org.ce.cc.core.entity.enumeration.JobState;
import org.ce.cc.core.entity.enumeration.Language;
import org.ce.cc.core.entity.enumeration.ResultState;
import org.ce.cc.core.repository.JobRepository;
import org.ce.cc.core.repository.ResultRepository;
import org.ce.cc.core.repository.UploadRepository;
import org.ce.cc.core.service.StorageServiceImpl;
import org.ce.cc.notifier.CodexMessage;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotifierService {

    private final UploadRepository uploadRepo;
    //    @Autowired
    private final ResultRepository resultRepository;
    //    @Autowired
    private final JobRepository jobRepository;

    private final StorageServiceImpl storageService;


    public void start(long uploadId) {
        Upload upload = uploadRepo.findByUploadId(uploadId);
        if (upload == null)
            return;
        System.out.println("Notifier: Upload object " + upload);
        //TODO read from s3. probably using the StorageServiceImpl and the line that I commented. also use 2 below methods.
        byte[] fileArrayBytes = storageService.downloadFile(String.valueOf(uploadId));
        String queryMapAsString = makeJobQueryString(fileArrayBytes, upload.getLanguage(), upload.getInput());
        System.out.println(queryMapAsString);

        // TODO the jobId is sequence. how I insert it into table?
        Job newJob = new Job();
        newJob.setUploadId(uploadId);
        newJob.setJobState(JobState.none);
        newJob.setJobQuery(queryMapAsString);
        jobRepository.save(newJob);

        Result result = new Result();
        result.setExecutionDate(java.sql.Date.valueOf(LocalDate.now()));
        result.setResultState(ResultState.in_progress);
        result.setJobId(newJob.getJobId());
        resultRepository.save(result);

    }


    public String makeJobQueryString(byte[] file, Language language, String input) {

        String fileAsString = convertBytesToString(file);
        CodexMessage codexMessage = new CodexMessage(fileAsString, language.name(), input);

//        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

//        Map<String, String> map = new HashMap<>();
//        Map<String, String> requestBody = new HashMap<>();
//
//        map.put("code", "print(int(input())+7)");
//        map.put("language", language.name() + "");
//
//        map.put("input", input + "");
//        return getRequestBody(requestBody);
//        map.add("code", fileAsString);
//        map.add("language", language.name());
//        map.add("input", input);
//        return covertMapToString(map);
        return test(codexMessage);
    }

    public String test(CodexMessage codexMessage) {
        String json;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writeValueAsString(codexMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

//    private String getRequestBody(Map<String, String> requestBody) {
//        StringBuilder builder = new StringBuilder();
//        for (Map.Entry<String, String> entry : requestBody.entrySet()) {
//            builder.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
//        }
//        return builder.toString();
//    }

    public String covertMapToString(Map<String, String> map) {
        StringBuilder mapAsString = new StringBuilder("{");
        for (String key : map.keySet()) {
//            mapAsString.append(key + "=" + map.get(key) + ", ");
            mapAsString.append(key + "").append("=").append(map.get(key) + "").append(", ");
        }
        mapAsString.delete(mapAsString.length() - 2, mapAsString.length()).append("}");
        return mapAsString.toString();
    }

    public String convertBytesToString(byte[] fileBytes) {
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

}
