package com.matrimony.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    String uploadImage(String path, MultipartFile uploadFile) throws IOException;
}
