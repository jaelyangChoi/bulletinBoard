package com.example.bulletinBoard.controller.validator;

import com.example.bulletinBoard.controller.form.PostForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PostFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PostForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostForm postForm = (PostForm) target;

        if(hasProfanity(postForm.getTitle())) {
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
