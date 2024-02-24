package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Controller.FileController;
import com.b2012202.mxhtknt.Repositories.UserRepository;
import com.b2012202.mxhtknt.Request.BaiVietRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Models.BaiViet;
import com.b2012202.mxhtknt.Models.EmbeddedId.PhongID;
import com.b2012202.mxhtknt.Models.File;
import com.b2012202.mxhtknt.Models.Phong;
import com.b2012202.mxhtknt.Models.User;
import com.b2012202.mxhtknt.Repositories.BaiVietRepository;
import com.b2012202.mxhtknt.Repositories.PhongRepository;
import com.b2012202.mxhtknt.Services.BaiVietService;
import com.b2012202.mxhtknt.Services.IStorageService;
import com.b2012202.mxhtknt.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BaiVietServiceImpl implements BaiVietService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PhongRepository phongRepository;
    private final IStorageService iStorageService;
    private final BaiVietRepository baiVietRepository;

    @Override
    public ResponseObject createBaiViet(BaiVietRequest baiVietRequest) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User existUser = userService.findUserByUsername(username);
            if (existUser == null) {
                return new ResponseObject("failed", "User info invalid", null);
            }
            PhongID phongID = PhongID.builder()
                    .idNhaTro(baiVietRequest.getIdNhaTro())
                    .idLau(baiVietRequest.getIdLau())
                    .idPhong(baiVietRequest.getIdPhong())
                    .build();
            Phong existPhong = phongRepository.findById(phongID).orElse(null);
            if (existPhong == null) {
                return new ResponseObject("failed", "Bai viet must have one Phong", null);
            }
            BaiViet baiViet = BaiViet.builder()
                    .description(baiVietRequest.getDescription())
                    .lock(baiVietRequest.isLock())
                    .user(existUser)
                    .published_at(LocalDateTime.now())
                    .phongSet(new HashSet<>())
                    .build();

            System.out.println("~~~PHONG SET: " + baiViet.getPhongSet());
            //Handle file
            List<MultipartFile> files = baiVietRequest.getFiles();
            if (!files.isEmpty()) {
                try {
                    List<String> filesURL = new ArrayList<>();
                    for (MultipartFile file : files) {
                        System.out.println("~~~>FILE: " + file.getOriginalFilename());
                        String fileName = iStorageService.storeFile(file);
                        if (fileName == null) {
                            return new ResponseObject("failed", "file format of some file invalid", null);
                        }

                        Path[] paths = iStorageService.loadFile(fileName).toArray(Path[]::new);
                        //Check if filePath is existing
                        if (paths.length > 0) {
                            Path filePath = paths[0];
                            String urlPath = MvcUriComponentsBuilder.fromMethodName(FileController.class,
                                    "readDetailFile", filePath.getFileName().toString()).build().toUri().toString();
//                            String baseUrl = "http://localhost:8080/api/v1/public/";
//                            String filename = filePath.getFileName().toString();
//                            String urlPath = baseUrl + "chutro/baiviet/" + filename;
                            System.out.println("~~~>fileURL: " + urlPath);
                            if (Objects.equals(urlPath, "")) {
                                return new ResponseObject("failed", "Failed to generate file path", null);
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
                        return new ResponseObject("failed", "Can not add file to fileSet", null);
                    }
                    baiViet.setFileSet(fileSet);
                    //add Phong to BaiViet
                    baiViet.getPhongSet().add(existPhong);
                    return new ResponseObject("ok", "Create Bai viet successfully", baiVietRepository.save(baiViet));
                } catch (Exception ex) {
                    return new ResponseObject("failed", ex.getCause().toString(), null);
                }
            }
            //Set Phong for BaiViet
            baiViet.getPhongSet().add(existPhong);

            return new ResponseObject("ok", "Create BaiViet successfully", baiVietRepository.save(baiViet));
        } catch (Exception e) {
            return new ResponseObject("failed", e.getMessage(), null);
        }
    }

    @Override
    public ResponseObject getByIdBaiViet(Long idBaiViet) {
        return new ResponseObject("ok", "Get bai viet by ID", userRepository.countLikesByIdBaiViet(idBaiViet));
//        return new ResponseObject("ok", "Get bai viet by ID", baiVietRepository.findById(idBaiViet).orElse(null));
    }

    @Override
    public byte[] getFileByte(String fileName) {
        return iStorageService.readFileContent(fileName);
    }

}
