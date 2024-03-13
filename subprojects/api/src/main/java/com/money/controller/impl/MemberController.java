package com.money.controller.impl;

import com.money.controller.MemberApi;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController implements MemberApi {

    @Override
    public String test() {
        return "OK";
    }
}
