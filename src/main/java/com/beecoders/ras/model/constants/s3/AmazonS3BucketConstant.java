package com.beecoders.ras.model.constants.s3;

public class AmazonS3BucketConstant {
    /* Patterns */
    public static final String LINK_PATTERN = "https://%s.s3.amazonaws.com/%s/%s";
    public static final String FOLDER_PATTERN = "%s/%s";
    public static final String IMAGE_PATTERN = "%s.%s";

    /* Error messages */
    public static final String EMPTY_IMAGE_ERROR_MESSAGE = "Image should not be empty";
    public static final String IMAGE_FORMAT_ERROR_MESSAGE = "Image format must be JPEG, PNG";
}
