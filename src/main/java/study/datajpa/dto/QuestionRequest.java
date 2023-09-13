package study.datajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
//Front단에서 요청하는 DTO
public class QuestionRequest implements Serializable {
    private String question;
}