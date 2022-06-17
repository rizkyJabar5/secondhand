package com.secondhand.ecommerce.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.secondhand.ecommerce.utils.SecondHandConst.HOME_PAGE;

@RestController
@RequestMapping(HOME_PAGE)
public class HomeController {

    @GetMapping
    public String home() {
        return "Halaman Depan";
    }

}
