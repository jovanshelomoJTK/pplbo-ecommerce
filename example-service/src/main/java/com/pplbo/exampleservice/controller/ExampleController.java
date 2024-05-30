package com.pplbo.exampleservice.controller;

import com.pplbo.exampleservice.jwt.customannotations.AllowedRoles; // tambahkan ini
import com.pplbo.exampleservice.jwt.customannotations.UserDataFromToken; // tambahkan ini
import com.pplbo.exampleservice.jwt.model.JwtUserData; // tambahkan ini
import com.pplbo.exampleservice.jwt.model.JwtUserData.Role; // tambahkan ini

import org.springframework.web.bind.annotation.RestController;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class ExampleController {

    @GetMapping("/admin-only")
    @AllowedRoles({ Role.ADMIN }) // isinya bisa {Role.ADMIN}, {Role.CUSTOMER}, atau {Role.ADMIN, Role.CUSTOMER}
    public JwtUserData myUserData(@UserDataFromToken JwtUserData userData) {
        return userData;
    }

    @GetMapping("/customer-only")
    @AllowedRoles({ Role.CUSTOMER })
    public JwtUserData myUserData2(@UserDataFromToken JwtUserData userData) {
        return userData;
    }

    @GetMapping("/admin-and-customer")
    @AllowedRoles({ Role.ADMIN, Role.CUSTOMER })
    public JwtUserData myUserData3(@UserDataFromToken JwtUserData userData) {
        return userData;
    }

    @GetMapping("/without-token")
    public JwtUserData myUserData4(@Nullable @UserDataFromToken JwtUserData userData) {
        return userData;
    }

}
