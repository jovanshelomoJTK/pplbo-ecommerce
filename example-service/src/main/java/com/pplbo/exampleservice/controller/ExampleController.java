package com.pplbo.exampleservice.controller;

// !!!!!!!!!!!!!! JANGAN LUPA IMPORT 4 CLASS INI
import com.pplbo.exampleservice.jwt.customannotations.AllowedRoles;
import com.pplbo.exampleservice.jwt.customannotations.UserDataFromToken;
import com.pplbo.exampleservice.jwt.model.JwtUserData;
import com.pplbo.exampleservice.jwt.model.JwtUserData.Role;

import org.springframework.web.bind.annotation.RestController;
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
    @AllowedRoles({ Role.CUSTOMER }) // isinya bisa {Role.ADMIN}, {Role.CUSTOMER}, atau {Role.ADMIN, Role.CUSTOMER}
    public JwtUserData myUserData2(@UserDataFromToken JwtUserData userData) {
        return userData;
    }

    @GetMapping("/admin-and-customer")
    @AllowedRoles({ Role.ADMIN, Role.CUSTOMER }) // isinya bisa {Role.ADMIN}, {Role.CUSTOMER}, atau {Role.ADMIN,
                                                 // Role.CUSTOMER}
    public JwtUserData myUserData3(@UserDataFromToken JwtUserData userData) {
        return userData;
    }

    @GetMapping("/my-role")
    @AllowedRoles({ Role.ADMIN, Role.CUSTOMER }) // isinya bisa {Role.ADMIN}, {Role.CUSTOMER}, atau {Role.ADMIN,
                                                 // Role.CUSTOMER}
    public Role myRole(@UserDataFromToken JwtUserData userData) {
        // masih ada get-get yang lain: getId(), getEmail(), getName()
        return userData.getRole();
    }

    // KALAU MAU BISA DIAKSES TANPA LOGIN, HAPUS ANNOTATION AllowedRoles

    // Di Swagger, akan ada button "Authorize" di kanan atas, klik dan masukkan
    // token yang ada di .env.example
}
