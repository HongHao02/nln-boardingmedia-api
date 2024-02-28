package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Controller.FileController;
import com.b2012202.mxhtknt.DTO.ConvertSetFileDTO;
import com.b2012202.mxhtknt.Models.File;
import com.b2012202.mxhtknt.Services.IStorageService;
import jakarta.servlet.http.HttpServlet;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Stream;
@Service
public class IStorageServiceImpl implements IStorageService {
    Logger logger = LoggerFactory.getLogger(IStorageServiceImpl.class);
    private final Path stogareFolder = Paths.get("uploads");//Tham chieu den thu muc chua anh, neu chua co
    public IStorageServiceImpl() {
        try {
            Files.createDirectories(stogareFolder);
        } catch (IOException e) {
            throw new RuntimeException("Cannot initialize stogare", e);
        }
    }
    private boolean isImageFile(MultipartFile file) {
        //use commons-io to check file name
//        <dependency>
//            <groupId>commons-io</groupId>
//            <artifactId>commons-io</artifactId>
//            <version>2.11.0</version>
//        </dependency>
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename()); //lay duoi file
        assert fileExtension != null;
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"}).contains(fileExtension.trim().toLowerCase());

    }
    @Override
    public String storeFile(MultipartFile file) {
        try {
            System.out.println("File load from client/ request~~~>");
            if (file.isEmpty()) {
//                throw new RuntimeException("Fail to store empty file");
                logger.error("Fail to store empty file");
                return "";
            }
            //check file is Image?
            if (!isImageFile(file)) {
//              throw new RuntimeException("You can only upload image file");
                logger.error("You can only upload image file");
                return "";
            }
            //file must be <= 5Mb
            float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
            if (fileSizeInMegabytes > 5.0f) {
//                throw new RuntimeException("File must be <= 5Mb");
                logger.error("File must be <= 5Mb");
                return "";
            }
            //file must be rename, boi vi khi ma gui len server chung ta phai luu file nay duoi dang ten khac, neu khong
            //se xay ra hien tuong trung file tren server lam file bi ghi de, xoa file cu
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename()); //lay duoi file
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destinationFilePath = this.stogareFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();

            if (!destinationFilePath.getParent().equals(this.stogareFolder.toAbsolutePath())) {
//                throw new RuntimeException("Cannot store file outside directory");
                logger.error("Cannot store file outside directory");
                return "";
            }
            //neu ma co ton tai thi thay the ghi de len
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;//Trong truong hop thanh cong
        } catch (IOException e) {
//            throw new RuntimeException("Fail to store file.", e);
//            e.printStackTrace();
            logger.error("The field file1 exceeds its maximum permitted size of 1048576 bytes.");
            return null;
        }
    }



    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.stogareFolder, 1)
                    .filter(path -> !path.equals(this.stogareFolder) && !path.toString().contains("._"))
                    .map(this.stogareFolder::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load store files ", e);
        }
    }

    @Override
    public Stream<Path> loadFile(String fileName) {
        Path filePath = this.stogareFolder.resolve(fileName);
        if (Files.exists(filePath)) {
            // Kiểm tra xem tệp tồn tại trong thư mục lưu trữ hay không
            return Stream.of(filePath);
        } else {
            throw new RuntimeException("File not found: " + fileName);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = stogareFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            } else {
                throw new RuntimeException("Could not read file " + fileName);
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + fileName, e);
        }
    }

    @Override
    public ConvertSetFileDTO convertToSetFile(List<MultipartFile> files) {
        try{
            List<String> filesURL = new ArrayList<>();
            for (MultipartFile file : files) {
                System.out.println("~~~>FILE: " + file.getOriginalFilename());
                String fileName = storeFile(file);
                if (fileName == null) {
                    return ConvertSetFileDTO.builder()
                            .status(415)
                            .message("Can not store file! Format or size is invalid")
                    .build();
                }
                Path[] paths = loadFile(fileName).toArray(Path[]::new);
                //Check if filePath is existing
                if (paths.length > 0) {
                    Path filePath = paths[0];
                    String urlPath = MvcUriComponentsBuilder.fromMethodName(FileController.class,
                            "readDetailFile", filePath.getFileName().toString()).build().toUri().toString();
                    System.out.println("~~~>fileURL: " + urlPath);
                    if (Objects.equals(urlPath, "")) {
                        return ConvertSetFileDTO.builder()
                                .status(100)
                                .message("Can not read urlPath!")
                                .build();
                    }
                    filesURL.add(urlPath);
                }
            }
            //add file to BaiViet
            Set<File> fileSet = new HashSet<>();
            if (!filesURL.isEmpty()) {
                for (String fileName : filesURL) {
                    File file = File.builder()
                            .url(fileName)
                            .build();
                    fileSet.add(file);
                }
            } else {
                return ConvertSetFileDTO.builder()
                        .status(101)
                        .message("Can set file in Set")
                        .build();
            }
            return ConvertSetFileDTO.builder()
                    .fileSet(fileSet)
                    .message("success")
                    .build();
        }catch (Exception ex){
            return ConvertSetFileDTO.builder()
                    .status(400)
                    .message(ex.getMessage())
                    .build();
        }
    }
}
