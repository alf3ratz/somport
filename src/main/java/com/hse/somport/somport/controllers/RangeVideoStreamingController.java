package com.hse.somport.somport.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class RangeVideoStreamingController {

    private final Path videoDirectory = Paths.get("videos");

    @GetMapping("/range-stream/{filename}")
    public ResponseEntity<byte[]> rangeStreamVideo(@PathVariable String filename, @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {
        File file = videoDirectory.resolve(filename).toFile();

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        long fileSize = file.length();
        long start = 0;
        long end = fileSize - 1;

        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] ranges = rangeHeader.substring(6).split("-");
            start = Long.parseLong(ranges[0]);
            end = ranges.length > 1 && !ranges[1].isEmpty() ? Long.parseLong(ranges[1]) : fileSize - 1;
        }

        long contentLength = end - start + 1;

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) contentLength];
            fis.skip(start);
            fis.read(data);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Range", "bytes " + start + "-" + end + "/" + fileSize);
            headers.add("Accept-Ranges", "bytes");
            headers.setContentType(MediaType.parseMediaType("video/mp4"));
            headers.setContentLength((int) contentLength);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .body(data);
        }
    }
}
