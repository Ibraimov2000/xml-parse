package com.example.xmlparse.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.*;

@Slf4j
@Service
public class DataService {

    private static final String ABSOLUTE_PATH = "D:\\IdeaProjects\\demir\\";
    private static final String FILE_SUFFIX = ".log";
    private static final String NUMBER_OF_RECORDS = "Number of records: ";
    private static final String EMPTY = "0";

    public String convert(String xml) throws IOException {
        JSONObject jsonObject = XML.toJSONObject(xml);
        log.info("IN convert: xml was converted to json: {}", jsonObject);
        writeToFile(jsonObject);
        return jsonObject.toString();
    }

    private void writeToFile(JSONObject jsonObject) throws IOException {
        String filename = ABSOLUTE_PATH + jsonObject.getJSONObject("Data").getString("Type") + "-" +
                jsonObject.getJSONObject("Data").getJSONObject("Creation").getString("Date").substring(0, 10) + FILE_SUFFIX;

        try (RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
            if (file.length() == 0) {
                file.write((NUMBER_OF_RECORDS + EMPTY).getBytes());
            } else {
                file.seek(file.length());
            }

            file.write('\n');
            file.write(jsonObject.toString().getBytes());

            file.seek(0);
            file.write((NUMBER_OF_RECORDS + (getLineCount(filename) - 1)).getBytes());

            log.info("IN saveToFile json was save to file: {}", filename);
        }
    }

    private int getLineCount(String filename) {
        int lineCount = 0;

        try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {
            long fileLength = file.length();
            long filePointer = 0;

            while (filePointer < fileLength) {
                file.seek(filePointer);

                int character = file.read();
                while (character != -1 && character != '\n') {
                    character = file.read();
                }

                lineCount++;
                filePointer = file.getFilePointer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }
}
