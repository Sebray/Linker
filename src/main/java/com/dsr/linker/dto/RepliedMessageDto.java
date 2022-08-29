package com.dsr.linker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepliedMessageDto {
    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "text")
    private String text;

    @JsonProperty(value = "lastName")
    private String lastName;

}
