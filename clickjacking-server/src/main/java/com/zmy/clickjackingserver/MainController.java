package com.zmy.clickjackingserver;

import java.util.Arrays;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @GetMapping("/target")
    public String targetPage() {
        return "target";
    }

    @GetMapping("/login")
    public String loginAndReturnTargetPage(HttpServletResponse response) {
        response.addCookie(new Cookie("session", "faketoken"));
        System.out.println("登录成功");
        return "target";
    }

    @GetMapping("/like/{id}")
    public String like(@PathVariable("id") String id, HttpServletRequest request) {
        Cookie session = getSession(request);

        if (ObjectUtils.isEmpty(session) || !"faketoken".equals(session.getValue())) {
            System.out.println("未登录，不可参与活动");
            return "target";
        }
        System.out.println("like:" + id);
        return "target";
    }
    private Cookie getSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (ObjectUtils.isEmpty(cookies)) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> "session".equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }
}
