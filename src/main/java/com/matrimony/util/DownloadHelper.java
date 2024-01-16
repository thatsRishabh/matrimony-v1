package com.matrimony.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class DownloadHelper {

    @Value("${project.upload}")
    private String path;


    @GetMapping("/file/download/{fileName}")
    public void downloadFile(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        try {
            String fullPath = path + File.separator + fileName;
            InputStream resource = new FileInputStream(fullPath);

            // Set the content type based on the file type (e.g., PDF, image, etc.)
            // Modify this according to the actual content type you're serving.
            // For example, for PDF files, you can set the content type as follows:
            // response.setContentType(MediaType.APPLICATION_PDF_VALUE);

            // Copy the file content to the response output stream
            StreamUtils.copy(resource, response.getOutputStream());
        } catch (IOException e) {
            // Handle the exception here, you can log it or return an error response.
            e.printStackTrace(); // You should replace this with proper error handling.

            // Set an appropriate response status and message for the client
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("text/plain");
            try {
                response.getWriter().write("An error occurred while downloading the file");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


}
