package com.gifmain.services;

import com.gifmain.models.Gif;
import com.gifmain.models.Tag;
import com.gifmain.repositories.GifRepository;

import com.gifmain.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
@Service
public class GifService {
    @Autowired
    GifRepository gifRepository;
    @Autowired
    TagRepository tagRepository;

    public Gif saveGif(Gif gif) {
//       TODO: connection to fileServer
        List<Tag> tags=gif.getTags();
        for(Tag t:tags){
            if(tagRepository.findById(t.getName()).isEmpty()){
                tagRepository.save(t);
            };
        }
        return gifRepository.save(gif);
    }

    public Gif getGif(int id) {
        return gifRepository.findById(id).orElse(null);
    }

    public List<Gif> getAllFiles() {
//        TODO:DO limit for query

        return gifRepository.findAll();
    }

    public boolean deleteGifById(int id){
        if(gifRepository.findById(id).isEmpty()){
            return false;
        }
        else gifRepository.deleteById(id);
        return true;
    }
}
