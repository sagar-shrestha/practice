package com.sagar.redispoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document implements Serializable {

    private String id;
    private String title;
    private String content;
    private String type;                 // e.g. "invoice", "report"
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;
}
