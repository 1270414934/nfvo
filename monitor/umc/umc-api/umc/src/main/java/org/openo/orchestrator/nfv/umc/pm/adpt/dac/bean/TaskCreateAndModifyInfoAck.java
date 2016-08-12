package org.openo.orchestrator.nfv.umc.pm.adpt.dac.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class TaskCreateAndModifyInfoAck
{
    private int status;
    private int taskId;
    private String ErrorMsg;
    private String DebugMsg;
}
