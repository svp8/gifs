package com.gifmain.services;

import com.gifmain.models.Gif;
import com.gifmain.models.Tag;
import com.gifmain.repositories.GifRepository;

import com.gifmain.repositories.TagRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Service
public class GifService {
    @Autowired
    GifRepository gifRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    WebClient webClient;

    public Gif saveGif(Gif gif, MultipartFile file) throws IOException {
        Gif ngif=gifRepository.save(gif);
        MultipartFile multipartFile = new MockMultipartFile( gif.getName()+"~"+ngif.getId(),gif.getName()+"~"+ngif.getId(),file.getContentType(), file.getInputStream());
        MultiValueMap<String, Object> multipartData = new LinkedMultiValueMap<>();
        multipartData.add("file", multipartFile.getResource());
        String status = webClient.post().uri("/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartData)).retrieve().bodyToMono(String.class).block();
        System.out.println(status);
        List<Tag> tags = gif.getTags();
        for (Tag t : tags) {
            if (tagRepository.findById(t.getName()).isEmpty()) {
                tagRepository.save(t);
            }
        }

        return gif;
    }

    public Gif getGif(int id) {
        Gif gif=gifRepository.findById(id).orElse(null);
        String url="http://localhost:8080/files/show/";
        if(gif!=null){
            gif.setUrl(url+gif.getName()+"~"+gif.getId());
        }
        return gif;
    }

    public List<Gif> getAllFiles() {
//        TODO:DO limit for query

        return gifRepository.findAll();
    }

    public boolean deleteGifById(int id) {
        if (gifRepository.findById(id).isEmpty()) {
            return false;
        } else gifRepository.deleteById(id);
        return true;
    }
}
