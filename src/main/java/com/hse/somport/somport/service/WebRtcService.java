package com.hse.somport.somport.service;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebRtcService {

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> sendOffer(String sdp, String type) {
        String url = "http://localhost:8081/offer";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("sdp", sdp);
        requestBody.put("type", type);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        return response.getBody();
    }

    private String generateSDPOffer() {
        return "v=0\r\n" +
                "o=- 0 0 IN IP4 127.0.0.1\r\n" +
                "s=WebRTC Client\r\n" +
                "t=0 0\r\n" +
                "a=group:BUNDLE video\r\n" +
                "m=video 9 UDP/TLS/RTP/SAVPF 96\r\n" +
                "a=rtpmap:96 VP8/90000\r\n" +
                "a=sendrecv\r\n";
    }
}

