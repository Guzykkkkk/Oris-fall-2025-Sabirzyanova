package org.example.FourthOctober.model;

import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Message {
    private Long id;
    private String message;
    private LocalDateTime time;
    private User author;


}
