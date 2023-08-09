package com.gifs.controllers;

import com.gifs.models.Gif;
import com.gifs.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequestMapping(path = "/files")
public class FileController {

    @Autowired
    FileService fileService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            System.out.println(file.getOriginalFilename());
            Gif gif=fileService.saveFile(file);

//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(gif.getName());
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable int id) {
        Gif fileDB = fileService.getFile(id);
        if(fileDB==null) return ResponseEntity.badRequest().body("No such file");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" )
                .body(fileDB);
    }
    @GetMapping("/show/{name}")
    public ResponseEntity<?> showFile(@PathVariable String name) {
        Gif fileDB = fileService.getFileByName(name);
        if(fileDB==null) return ResponseEntity.badRequest().body("No such file");
        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" )
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileDB.getData());
    }
    @GetMapping("/all")
    public ResponseEntity<List<Gif>> getAllFiles() {
        List<Gif> fileDB = fileService.getAllFiles();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=324" )
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileDB);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFileById(@PathVariable int id){
        if(fileService.deleteFileById(id)){
            return  ResponseEntity.ok().body(String.format("Deleted file with id:%d",id));
        }
        else return ResponseEntity.badRequest().body(String.format("Failes to delete file with id:%d",id));
    }
}
