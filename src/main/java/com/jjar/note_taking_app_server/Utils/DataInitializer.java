package com.jjar.note_taking_app_server.Utils;

import com.jjar.note_taking_app_server.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {

    @Autowired
    private CategoryService categoryService;

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        List<String> presetCategories = List.of("Work", "Personal", "Urgent", "Miscellaneous");
        for (String categoryName : presetCategories) {
            categoryService.getOrCreateCategory(categoryName);
        }
    }
}
