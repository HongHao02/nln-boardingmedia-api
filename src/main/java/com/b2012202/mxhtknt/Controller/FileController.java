package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Services.IStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/file")
@RequiredArgsConstructor
public class FileController {
    private final IStorageService iStorageService;
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(iStorageService.readFileContent(fileName));//dung thay cho HttpStatus.OK
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
