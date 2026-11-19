package com.boosterltd.iptbank.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "news")
@AllArgsConstructor @Data @NoArgsConstructor
public class NewsEntity {
    @Column(name = "id", unique = true, nullable = false) @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String content;
}
