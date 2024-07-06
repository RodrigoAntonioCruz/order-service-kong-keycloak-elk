package com.example.core.ports.input;

import java.io.InputStream;
public interface UploadOrderFileInputPort {
    void upload(InputStream inputStream, String originalFilename);
}