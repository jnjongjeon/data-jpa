package study.datajpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import study.datajpa.dto.ChatGptResponse;
import study.datajpa.dto.QuestionRequest;
import study.datajpa.dto.UrlDto;
import study.datajpa.service.ChatBotService;
import study.datajpa.service.ChatGptService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final ChatBotService chatBotService;
    private final ChatGptService chatGptService;
    //카카오톡 오픈빌더로 리턴할 스킬 API
    @RequestMapping(value = "/v1", method = {RequestMethod.POST, RequestMethod.GET}, headers = {"Accept=application/json"})
    public HashMap<String, Object> callAPI(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws InterruptedException {

        HashMap<String, Object> resultJson = new HashMap<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(params);
            System.out.println(jsonInString);
            HashMap<String, Object> userRequest = (HashMap<String, Object>) params.get("userRequest");
            String url = userRequest.get("callbackUrl").toString();
            String utter = userRequest.get("utterance").toString().replace("\n","");
            System.out.println(utter);
            System.out.println(url);
            resultJson.put("version", "2.0");
            resultJson.put("useCallback", true);

            chatBotService.aa(url, utter);
        } catch (Exception e) {

        }
        return resultJson;
    }

    @RequestMapping(value = "/v2", method = {RequestMethod.POST, RequestMethod.GET})
    public void callAPI2(@RequestBody UrlDto urlDto) throws InterruptedException {
        HashMap<String, Object> resultJson = new HashMap<>();

        try {
            resultJson.put("version", "2.0");
            List<HashMap<String, Object>> outputs = new ArrayList<>();
            HashMap<String, Object> template = new HashMap<>();
            HashMap<String, Object> simpleText = new HashMap<>();
            HashMap<String, Object> text = new HashMap<>();

            ChatGptResponse chatGptResponse = chatGptService.askQuestion(new QuestionRequest(urlDto.getUtter()));

            text.put("text", chatGptResponse.getChoices().get(0).getMessage().getContent());
            simpleText.put("simpleText", text);
            outputs.add(simpleText);
            template.put("outputs", outputs);
            resultJson.put("template", template);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<HashMap<String, Object>> hashMapHttpEntity = new HttpEntity<>(resultJson, headers);
            RestTemplate rt = new RestTemplate();

            rt.exchange(urlDto.getUrl().toString(), HttpMethod.POST, hashMapHttpEntity, Map.class);

        } catch (Exception e) {

        }
    }
}
