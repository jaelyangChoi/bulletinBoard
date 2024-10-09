package com.example.bulletinBoard.web.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
public class PostFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PostForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostForm postForm = (PostForm) target;

        if (hasProfanity(postForm.getTitle())) {
            //사용자 입력값 유지 및 에러 메시지를 errors 에 담는다.
            errors.rejectValue("title", "profanity");
            errors.rejectValue("content", "");
            errors.rejectValue("categoryId", "");
            errors.rejectValue("secretYN", "");
        }
    }


    private boolean hasProfanity(String title) {
        return title.contains("fuck");
    }
}
