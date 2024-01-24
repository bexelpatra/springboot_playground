package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Basic;

import com.example.demo.entity.compositeKey.BlockCahinBackUpHistKey;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="TB_BLOCKCHAIN_BACKUP_HIST",schema="TEST_DB")
public class BlockCahinBackUpHist {
    @EmbeddedId
    private BlockCahinBackUpHistKey pk;

    @Basic
    @Column(name = "SVC_ID")
    private String svcId;

    @Basic
    @Column(name = "MOBILE_DRVLCNSE_REGIST_DT")
    private Timestamp mobileDrvlcnseRegistDt;

    @Basic
    @Column(name = "TELECOM_CODE")
    private String telecomCode;
    @Basic
    @Column(name = "DRVLCNSE_TIMESTAMP")
    private String drvlcnseTimestamp;
    @Basic
    @Column(name = "MOBILE_DRVLCNSE_REGIST_STTUS_CODE")
    private String mobileDrvlcnseRegistSttusCode;
    @Basic
    @Column(name = "USER_PBLKY")
    private String userPblky;
    @Basic
    @Column(name = "UUID")
    private String uuid;
    @Basic
    @Column(name = "MOBILE_DRVLCNSE_HASH")
    private String mobileDrvlcnseHash;
    @Basic
    @Column(name = "SM_USER_PBLKY")
    private String smUserPblky;
    @Basic
    @Column(name = "REGIST_DT")
    private Timestamp registDt;
    @Basic
    @Column(name = "REGISTER_ID")
    private String registerId;
    @Basic
    @Column(name = "REGIST_PROGRM_ID")
    private String registProgrmId;
    @Basic
    @Column(name = "UPDT_DT")
    private Timestamp updtDt;
    @Basic
    @Column(name = "UPDUSR_ID")
    private String updusrId;
    @Basic
    @Column(name = "UPDT_PROGRM_ID")
    private String updtProgrmId;
}
