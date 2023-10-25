package com.bioproj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
@Slf4j
public class FileController {
    @Value("${scriptDir}")
    String scriptDir;


    private static void listFilesWithExtension(File directory, String fileExtension, List<File> fileList) {
        File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(fileExtension);
            }
        });

        if (files != null) {
            for (File file : files) {
                fileList.add(file); // 将符合条件的文件添加到列表中
            }
        }

        // 递归遍历子目录
        File[] directories = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        if (directories != null) {
            for (File subdir : directories) {
                listFilesWithExtension(subdir, fileExtension, fileList);
            }
        }
    }

    @GetMapping("/list")
    public List<String> list(){
//        String directoryPath = "目录路径"; // 替换为您要遍历的目录路径
        String fileExtension = ".nf"; // 替换为您要筛选的文件后缀
        File directory = Paths.get(scriptDir).toFile();
        if (directory.exists() && directory.isDirectory()) {
            List<File> fileList = new ArrayList<>();
            listFilesWithExtension(directory, fileExtension, fileList);
            // 现在 fileList 包含了符合条件的文件列表
            List<String> fileListStr = fileList.stream().map(file -> file.getAbsolutePath()).collect(Collectors.toList());
            return fileListStr;
//            for (File file : fileList) {
//                System.out.println(file.getAbsolutePath());
//            }
        }
        throw new RuntimeException("file is not exist!");

    }
}
