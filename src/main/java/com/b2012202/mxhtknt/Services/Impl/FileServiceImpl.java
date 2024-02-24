package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Repositories.FileRepository;
import com.b2012202.mxhtknt.Services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
}
