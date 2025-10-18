package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {
    private int taskId;
    private String title;
    private String description;
    private String status;
    private String user_email;
    private int user_id;

}

