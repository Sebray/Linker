package com.dsr.linker.dto;

import com.dsr.linker.dto.account.AccountForChatDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstablishedChatChannelDto {
    private String ChatUuid;
    private AccountForChatDto firstAccount;
    private AccountForChatDto secondAccount;
}
