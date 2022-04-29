package com.freecoder.enlearn.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Frank
 * @date 2022/4/28 13:30
 */
@Component
public class HttpHelper {
    static String url = "https://dict.youdao.com/dictvoice?audio=";

    RestTemplate restTemplate = new RestTemplate();

    public byte[] getMp3(String word) {
        return restTemplate.getForObject(url + word, byte[].class);
    }


}
