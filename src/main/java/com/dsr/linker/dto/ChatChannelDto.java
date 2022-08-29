package com.dsr.linker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatChannelDto {
    private Long firstAccId;
    private Long secondAccId;
}
