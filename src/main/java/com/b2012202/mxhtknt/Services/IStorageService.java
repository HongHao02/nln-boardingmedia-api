package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.DTO.ConvertSetFileDTO;
import com.b2012202.mxhtknt.Models.File;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface IStorageService {
    public String storeFile(MultipartFile file);

    public Stream<Path> loadAll(); //load all file in the folder

    public Stream<Path> loadFile(String fileName); //load all file in the folder

    //Lay thong tin anh bang mang byte, co the gui request len server cho server tra ve mang byte tu
    //do ta co the xem thong tin truc tiep tren trinh duyet
    public byte[] readFileContent(String fileName);
    public ConvertSetFileDTO convertToSetFile(List<MultipartFile> files);
}
