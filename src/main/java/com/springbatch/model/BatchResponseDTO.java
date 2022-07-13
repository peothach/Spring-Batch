package com.springbatch.model;

import java.time.LocalDate;
import java.util.Date;

//Please note that Line implements Serializable.
//That is because Line will act as a DTO to transfer data between steps.
//According to Spring Batch, objects that are transferred between steps must be serializable.
public class BatchResponseDTO{

    private String status;
    private Long jobId;
    private Long executionId;
    private Date createAt;
    public BatchResponseDTO(String status, Long jobId, Long executionId, Date createAt) {
        this.status = status;
        this.jobId = jobId;
        this.executionId = executionId;
        this.createAt = createAt;
    }

    public Long getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Long executionId) {
        this.executionId = executionId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
