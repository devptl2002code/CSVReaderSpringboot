package com.devptl.CSVDemo.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    boolean hasCsvFormat(MultipartFile file);

    void processAndSaveData(MultipartFile file);
}
