package org.ce.cc.executor.service;

import com.mailgun.model.message.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.ce.cc.core.entity.Job;
import org.ce.cc.core.entity.Result;
import org.ce.cc.core.entity.enumeration.JobState;
import org.ce.cc.core.entity.enumeration.ResultState;
import org.ce.cc.core.repository.JobRepository;
import org.ce.cc.core.repository.ResultRepository;
import org.ce.cc.core.repository.UploadRepository;
import org.ce.cc.core.service.StorageServiceImpl;
import org.ce.cc.executor.invoker.CodeXInvoker;
import org.ce.cc.executor.invoker.dto.CodeXExecutionRequestDto;
import org.ce.cc.executor.invoker.dto.CodeXExecutionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CodeXService {

    @PersistenceContext
    private EntityManager entityManager;
    private final CodeXInvoker invoker;
    private final UploadRepository uploadRepository;
    private final JobRepository jobRepository;
    private final ResultRepository resultRepository;
    @Autowired
    private final MailgunService mailgunService;

    private final StorageServiceImpl storageService;

    @Scheduled(fixedDelay = 3000)
    public void sendData() throws UnsupportedEncodingException {

        List<Job> jobsList = entityManager.createNativeQuery("SELECT * FROM JOB WHERE JOB_STATE = :status", Job.class)
                .setParameter("status", JobState.none.name())
                .getResultList();

        for (Job jb : jobsList) {
            String code = jb.getJobQuery();
            String language = uploadRepository.findByUploadId(jb.getUploadId()).getLanguage().name();
            String input = uploadRepository.findByUploadId(jb.getUploadId()).getInput();
//            String code = "print(int(input())+7)";
//            String language = "py";
//            String input = "5";

            CodeXExecutionResponseDto response = invoker.executeCode(new CodeXExecutionRequestDto(code, language, input));
            String receiverEmail = uploadRepository.findByUploadId(jb.getUploadId()).getEmail();

//            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
            MessageResponse messageResponse = mailgunService.sendSimpleMessage(response, receiverEmail);
            System.out.println("Codex Service in executor: " + "Message sending to email response - Mailgun" + messageResponse);
//            jobRepository.delete(jb);
            jb.setJobState(JobState.executed);
            jobRepository.save(jb);

//
//            // TODO delete from s3
//            String deleteFileFromS3Result = storageService.deleteFile(String.valueOf(jb.getUploadId()));
//            System.out.println(deleteFileFromS3Result);
//

            Result res = resultRepository.findResultByJobId(jb.getJobId());
            res.setOutPut(response.toString());
            res.setResultState(ResultState.done);
            resultRepository.save(res);

//            }
//            else {
//            }
            // TODO use send_code method

        }

    }


}

