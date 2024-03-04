package com.devptl.CSVDemo.controller;


import com.devptl.CSVDemo.response.ResponseMessage;
import com.devptl.CSVDemo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/*@RestController is a combination of @Controller and @ResponseBody*/
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService service;

    /*Create first endpoint to upload file*/

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uplaodfile(@RequestParam("file")MultipartFile file){
        if (service.hasCsvFormat(file)){
            service.processAndSaveData(file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Uploaded File successfully : " + file.getOriginalFilename()));

        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Please upload CSV file."));


    }
}
