package com.b2012202.mxhtknt.DTO;

import com.b2012202.mxhtknt.Models.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConvertSetFileDTO {
    private Set<File> fileSet = new HashSet<>();
    private int status;
    private String message;
}
