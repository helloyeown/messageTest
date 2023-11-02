package com.example.demo.model.dto.messages;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class GetMessages {
    
    private Long id;

    private String text;

    private LocalDateTime sendedDt;

    private String sender;

    private String receiver;

    private boolean isRead;

}
