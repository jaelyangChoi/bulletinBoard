package com.example.bulletinBoard.web.login;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //파라미터에 붙일 애노에티션
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
}
