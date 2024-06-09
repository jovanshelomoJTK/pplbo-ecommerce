package com.pplbo.productservice.jwt.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.pplbo.productservice.jwt.customannotations.UserDataFromToken;
import com.pplbo.productservice.jwt.model.JwtUserData;
import com.pplbo.productservice.jwt.util.JwtUtil;

@Component
public class UserDataArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(UserDataFromToken.class) != null
                && parameter.getParameterType().equals(JwtUserData.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // Extract the token from the Authorization header
        String authorizationHeader = webRequest.getHeader("Authorization");
        if (authorizationHeader == null) {
            return null;
        }

        String token = authorizationHeader.substring(7);

        // Decode and parse the token to create a JwtUserData object
        JwtUserData userData = jwtUtil.getUserData(token);

        return userData;
    }
}