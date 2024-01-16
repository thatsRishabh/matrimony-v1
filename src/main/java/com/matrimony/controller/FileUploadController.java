package com.matrimony.controller;

import com.matrimony.response.FileUploadResponse;
import com.matrimony.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @Value("${project.upload}")
    private String path;

    @PostMapping("/fileupload")
    public ResponseEntity<FileUploadResponse> fileUpload(@RequestParam("uploadFile") MultipartFile uploadFile)  {

        String fileName = null;
        String downloadURl = null;
        String fileType = null;
        Long fileSize = null;
        try {
            fileSize=uploadFile.getSize();
            fileType=uploadFile.getContentType();
            fileName=this.fileUploadService.uploadImage(path,uploadFile);

            //  // to get relative URL with the host

//            downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/file/download/")
//                    .path(fileName)
//                    .toUriString();

            // Below is to get relative URL without the host
            downloadURl = "/file/download/" + fileName;
        }catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(new FileUploadResponse(null,null, fileType,fileSize,"file not uploaded") , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new FileUploadResponse(fileName,downloadURl, fileType, fileSize,"file  uploaded successfully"), HttpStatus.OK);
    }

}

