package study.datajpa.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import study.datajpa.dto.UrlDto;

import java.util.Map;

@Service
public class ChatBotService {

    @Async
    public void aa(String url, String utter){

        UrlDto urlDto = new UrlDto();
        urlDto.setUrl(url);
        urlDto.setUtter(utter);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UrlDto> urlDtoHttpEntity = new HttpEntity<>(urlDto, headers);
        RestTemplate rt = new RestTemplate();
        rt.exchange("http://localhost:8080/v2", HttpMethod.POST, urlDtoHttpEntity, String.class);
    }
}
