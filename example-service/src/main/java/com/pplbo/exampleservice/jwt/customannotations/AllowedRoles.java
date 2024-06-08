package com.pplbo.exampleservice.jwt.customannotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import com.pplbo.exampleservice.jwt.model.JwtUserData.Role;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@SecurityRequirement(name = "Bearer Token")
public @interface AllowedRoles {
    Role[] value() default { Role.ADMIN, Role.CUSTOMER };
}