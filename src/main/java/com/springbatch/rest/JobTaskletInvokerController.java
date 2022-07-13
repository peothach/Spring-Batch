package com.springbatch.rest;

import com.springbatch.model.BatchResponseDTO;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/batch/tasklet")
public class JobTaskletInvokerController {

    @Autowired
    @Qualifier("asyncJobLauncher")
    JobLauncher jobLauncher;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    Job taskletJob;

    @GetMapping("/create-job")
    public ResponseEntity<BatchResponseDTO> invokeBatchTasklet() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        JobExecution execution = jobLauncher.run(taskletJob, jobParameters);
        return ResponseEntity
                .ok(new BatchResponseDTO(
                        execution.getStatus().toString(),
                        execution.getJobId(),
                        execution.getId(),
                        execution.getCreateTime())
                );
    }

    @GetMapping("/check-job/{executionId}")
    public ResponseEntity<BatchResponseDTO> getStatusBatchTasklet(@PathVariable("executionId") Long executionId) throws Exception {
        JobExecution jobExecution = jobExplorer.getJobExecution(executionId);

        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            return ResponseEntity
                    .ok(new BatchResponseDTO(
                            BatchStatus.FAILED.toString(),
                            jobExecution.getJobId(),
                            jobExecution.getId(),
                            jobExecution.getCreateTime())
                    );
        }

        return ResponseEntity
                .ok(new BatchResponseDTO(
                        jobExecution.getStatus().toString(),
                        jobExecution.getJobId(),
                        jobExecution.getId(),
                        jobExecution.getCreateTime())
                );
    }
}
