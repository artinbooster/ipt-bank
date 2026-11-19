package com.boosterltd.iptbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boosterltd.iptbank.model.entity.NewsEntity;

public interface NewsRepository extends JpaRepository<NewsEntity, String> {  }
