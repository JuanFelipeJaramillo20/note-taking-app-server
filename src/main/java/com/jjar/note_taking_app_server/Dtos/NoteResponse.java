package com.jjar.note_taking_app_server.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
public class NoteResponse {
    private Long id;
    private String title;
    private String content;
    private boolean archived;
    private Set<CategoryResponse> categories;

}
