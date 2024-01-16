package com.matrimony.service.serviceImpl;

import com.matrimony.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

@Service
public class FileUploadServiceImpl  implements FileUploadService {

    @Override
    public String uploadImage(String path, MultipartFile uploadFile) throws IOException {

//        file name
        String name = uploadFile.getOriginalFilename();

//        String randomId= UUID.randomUUID().toString();
        String currentTimestamp = String.valueOf(Instant.now().toEpochMilli());
        String name1=currentTimestamp.concat(name.substring(name.lastIndexOf(".")));
//        full path
        String filePath = path + File.separator + name1;

//        create a folder if not created
        File f= new File(path);
        if (!f.exists()){
            f.mkdir();
        }

//        file copy
        Files.copy(uploadFile.getInputStream(), Paths.get(filePath));

        return name1;
    }

}
