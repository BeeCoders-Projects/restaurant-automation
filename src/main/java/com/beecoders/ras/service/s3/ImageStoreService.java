package com.beecoders.ras.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.beecoders.ras.exception.s3.EmptyImageException;
import com.beecoders.ras.exception.s3.IncorrectImageFormatException;
import com.beecoders.ras.exception.s3.UploadImageException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static com.beecoders.ras.model.constants.s3.AmazonS3BucketConstant.*;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service
@RequiredArgsConstructor
public class ImageStoreService {
    @Value("${aws.bucket.name}")
    private String bucketName;
    private final AmazonS3 s3;

    public String uploadImage(MultipartFile image, String folder, Long id) {
        verifyImage(image);

        String filename = getFilename(id, image);
        String folderKey = getFolderKey(folder);

        try {
            InputStream inputStream = image.getInputStream();
            saveImage(folderKey, filename, inputStream);
        } catch (IOException e) {
            throw new UploadImageException(e.getMessage());
        }

        return generateUrl(folder, filename);
    }

    private void saveImage(String key, String filename, InputStream inputStream) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(inputStream.available());
        s3.putObject(key, filename, inputStream, metadata);
    }

    private void verifyImage(MultipartFile image) {
        isImageEmpty(image);
        validateCorrectImageFormat(image);
    }

    private void validateCorrectImageFormat(MultipartFile image) {
        if(!isPng(image) && !isJpeg(image)){
            throw new IncorrectImageFormatException(IMAGE_FORMAT_ERROR_MESSAGE);
        }
    }

    private boolean isPng(MultipartFile image) {
        return Objects.equals(image.getContentType(), IMAGE_PNG.getMimeType());
    }

    private boolean isJpeg(MultipartFile image) {
        return Objects.equals(image.getContentType(), IMAGE_JPEG.getMimeType());
    }

    private void isImageEmpty(MultipartFile image) {
        if(image.isEmpty()){
            throw new EmptyImageException(EMPTY_IMAGE_ERROR_MESSAGE);
        }
    }

    private String generateUrl(String folder, String filename) {
        return String.format(LINK_PATTERN, bucketName, folder, filename);
    }

    private String getFolderKey(String folder) {
        return String.format(FOLDER_PATTERN, bucketName, folder);
    }
    private String getFilename(Long id, MultipartFile image) {
        return String.format(IMAGE_PATTERN, id.toString(), getFileExtension(image));
    }

    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        int dotIndex = StringUtils.lastIndexOf(originalFilename, '.');
        return StringUtils.substring(originalFilename, dotIndex + 1);
    }

}
