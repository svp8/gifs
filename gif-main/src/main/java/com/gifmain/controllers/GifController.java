package com.gifmain.controllers;

import com.gifmain.models.Gif;
import com.gifmain.services.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/gif")
public class GifController {

    @Autowired
    GifService gifService;

    @PostMapping()
    public ResponseEntity<?> uploadGif(@RequestBody Gif gif) {
        String message = "";
        try {
            gifService.saveGif(gif);

            message =String.format("The file %s was successfully uploaded ",gif.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + gif.getName() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable int id) {
        Gif gif=gifService.getGif(id);
        if(gif==null) return ResponseEntity.badRequest().body("No such file");
        return ResponseEntity.ok()
                .body(gif);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Gif>> getAllFiles() {
        List<Gif> fileDB = gifService.getAllFiles();

        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" )
                .body(fileDB);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFileById(@PathVariable int id){
        if(gifService.deleteGifById(id)){
            return  ResponseEntity.ok().body(String.format("Deleted file with id:%d",id));
        }
        else return ResponseEntity.badRequest().body(String.format("Failed to delete the file with id:%d",id));
    }

}
