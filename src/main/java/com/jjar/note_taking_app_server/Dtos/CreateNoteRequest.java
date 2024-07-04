package com.jjar.note_taking_app_server.Dtos;

import java.util.List;

public class CreateNoteRequest {
    private String title;
    private String content;
    private List<String> categoryNames;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }
}
