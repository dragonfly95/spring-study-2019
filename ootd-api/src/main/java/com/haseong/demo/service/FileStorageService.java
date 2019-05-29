package com.haseong.demo.service;

import com.haseong.demo.config.FileStorageProperties;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

  public String storeFile(MultipartFile file);

  public Resource loadFileAsResource(String fileName);
}


