package com.secondhand.ecommerce.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.secondhand.ecommerce.models.entity.Images;
import com.secondhand.ecommerce.models.entity.UploadResponse;
import com.secondhand.ecommerce.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImagesRepository imagesRepository;

    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "secondhandk2",
            "api_key", "612217844351516",
            "api_secret", "Efcd1QUtlgJO7FAdO4M5Vw7hag8"));

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        UploadResponse response = new UploadResponse();
        File file = new File(image.getOriginalFilename());
        FileOutputStream os = new FileOutputStream(file);
        os.write(image.getBytes());
        os.close();
        Map result = cloudinary.uploader().upload(file,
                ObjectUtils.asMap("image_id", "product_image"));
        response.setMessage("Upload successful");
        String[] url = new String[1];
        url[0] = result.get("url").toString();
        response.setUrl(url);
        Images images = new Images();
        images.setImageName(image.getOriginalFilename());
        images.setImageFile(image.getBytes());
        imagesRepository.save(images);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Transactional
    @GetMapping(
            value = "/download/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<byte[]> getFile(@PathVariable("id") Long id) {
        Images image = imagesRepository.getById(id);
        InputStream in = getClass().getResourceAsStream(image.getImageName());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getImageName() + "\"")
                .body(image.getImageFile());
    }

    @PostMapping("/upload/multiple")
    public ResponseEntity<UploadResponse> uploadMultiple(@RequestParam("files") MultipartFile[] files) throws IOException {
        UploadResponse response = new UploadResponse();
        Integer size = files.length;
        String[] url = new String[size];
        for(int i = 0; i < size; i++) {
            File file = new File(files[i].getOriginalFilename());
            FileOutputStream os = new FileOutputStream(file);
            os.write(files[i].getBytes());
            os.close();
            Map result = cloudinary.uploader().upload(file,
                    ObjectUtils.asMap("image_id", "product_image"));

            url[i] = result.get("url").toString();
        }
        response.setMessage("Upload successful");
        response.setUrl(url);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}

