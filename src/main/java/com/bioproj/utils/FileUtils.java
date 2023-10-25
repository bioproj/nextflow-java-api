package com.bioproj.utils;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FileUtils {

    public static String openFile(File file){
        FileInputStream fileInputStream=null;
        try {
            if(file.exists()){
                fileInputStream = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
                List<String> list = reader.lines().collect(Collectors.toList());
                String content = Joiner.on("\n").join(list);
                return content;
            }else {
                return "Page is not found!!";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "Page is not found!!";
        }finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static  void saveFile(File file,String content){
        File parentFile = file.getParentFile();
        if(!parentFile.exists()){
            try {
                Files.createDirectories(parentFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream  = new FileOutputStream(file);

            fileOutputStream.write(content.getBytes());
            log.info("写入文件：{}",file.getPath().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
