package com.dsr.linker.dto;

import com.dsr.linker.entity.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipDto {
    @JsonProperty(value = "fistAccId")
    private Long firstAccId;

    @JsonProperty(value = "secondAccId")
    private Account secondAccId;

    @JsonProperty(value = "statusId")
    private Long statusId;

    @JsonProperty(value = "sender")
    private Byte sender = 0; //-1 – первый пользователь, 1 – второй

}
