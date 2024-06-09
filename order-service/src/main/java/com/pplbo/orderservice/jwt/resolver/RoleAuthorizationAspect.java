package com.pplbo.orderservice.jwt.resolver;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pplbo.orderservice.jwt.customannotations.AllowedRoles;
import com.pplbo.orderservice.jwt.model.JwtUserData;
import com.pplbo.orderservice.jwt.model.JwtUserData.Role;
import com.pplbo.orderservice.jwt.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RoleAuthorizationAspect {

    @Autowired
    JwtUtil jwtUtil;

    @Around("@annotation(com.pplbo.orderservice.jwt.customannotations.AllowedRoles)")
    public Object checkRole(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        AllowedRoles allowedRoles = method.getAnnotation(AllowedRoles.class);
        Role[] roles = allowedRoles.value();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.substring(7);

        // Decode and parse the token to create a JwtUserData object
        JwtUserData userData = jwtUtil.getUserData(token);

        // Check if the user has one of the allowed roles
        boolean authorized = false;
        for (Role role : roles) {
            if (userData.getRole() == role) {
                authorized = true;
                break;
            }
        }

        // If the user has one of the allowed roles, proceed with the method execution
        if (authorized) {
            return joinPoint.proceed();
        } else {
            throw new IllegalAccessException("User does not have the required role");
        }
    }
}
