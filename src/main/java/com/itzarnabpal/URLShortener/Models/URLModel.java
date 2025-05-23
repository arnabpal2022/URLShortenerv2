package com.itzarnabpal.URLShortener.Models;


import com.fasterxml.jackson.annotation.JsonView;
import com.itzarnabpal.URLShortener.Views.JSONView;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Date;


@Document(collection = "urls")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class URLModel {

    @MongoId
    @JsonView(JSONView.Default.class)
    private String id;

    @NotBlank
    @JsonView(JSONView.Default.class)
    private String url;

    @JsonView(JSONView.Default.class)
    private String shortCode;

    @JsonView(JSONView.Default.class)
    private Date createdAt;

    @JsonView(JSONView.Default.class)
    private Date updatedAt;

    @JsonView(JSONView.Stats.class)
    private int accessCount;
}

// The URLModel class is mapped to MongoDB using Spring Data MongoDB annotation @Document.
// The class is configured to store documents in a collection named urls.
