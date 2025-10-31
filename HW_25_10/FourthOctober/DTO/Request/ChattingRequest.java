package org.example.FourthOctober.DTO.Request;

import lombok.*;

import java.sql.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ChattingRequest {
   private Long message_id;
    private String message;
    private Date date;
    private Long author_id;

}
