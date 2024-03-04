package com.devptl.CSVDemo.service;

import com.devptl.CSVDemo.entity.User;
import com.devptl.CSVDemo.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private UserRepository repository;

    @Override
    public boolean hasCsvFormat(MultipartFile file) {
        String type = "text/csv";
        if (!type.equals(file.getContentType())){
            return false;
        }
        return true;
    }

    @Override
    public void processAndSaveData(MultipartFile file) {
        try {
            List<User> users = csvToUsers(file.getInputStream());
            repository.saveAll(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<User> csvToUsers(InputStream inputStream) {
            try {
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
                List<User> users = new ArrayList<User>();
                List<CSVRecord> records = csvParser.getRecords();

                for (CSVRecord csvRecord : records){
                    User user = new User(Long.parseLong(csvRecord.get("Index")), csvRecord.get("Height(Inches)"), csvRecord.get("Weight(Pounds)"));
                    users.add(user);
                }
                return users;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}
