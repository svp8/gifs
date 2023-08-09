package com.gifs.services;

import com.gifs.models.Gif;
import com.gifs.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;

    public Gif saveFile(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Gif file = new Gif(fileName, multipartFile.getContentType(), multipartFile.getBytes());
        return fileRepository.save(file);
    }

    public Gif getFile(int id) {
        return fileRepository.findById(id).orElse(null);
    }
    public Gif getFileByName(String name) {
        return fileRepository.findByName(name);
    }
    public List<Gif> getAllFiles() {
        return fileRepository.findAll();
    }

    public boolean deleteFileById(int id){
        if(fileRepository.findById(id).isEmpty()){
            return false;
        }
        else fileRepository.deleteById(id);
        return true;
    }
}
