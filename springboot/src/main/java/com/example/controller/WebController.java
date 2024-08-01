package com.example.controller;
import java.util.UUID;
import com.example.common.Result;
import com.example.common.RoleEnum;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.service.AdminService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {


    @Resource
    AdminService adminService;

    @Resource
    UserService userService;

    /**
     * 默认请求接口
     */
    @GetMapping("/")
    public Result hello() {
        return Result.success();
    }


    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        // 生成随机的 token 值
        String token = UUID.randomUUID().toString();

        // 创建 Cookie
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .path("/")
                .maxAge(3600)
                .build();
        // 设置到请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie.toString());

        if (RoleEnum.ADMIN.name().equals(account.getRole())){
            account =  adminService.login(account);
        }else if (RoleEnum.USER.name().equals(account.getRole())){
            account =  userService.login(account);
        }else{
            return Result.error("您的参数输入有误！");
        }
        return Result.success(account);
    }


    // 退出登录
        @PostMapping("/logout")
        public ResponseEntity<String> logout() {
            // 创建一个空的 Cookie 来覆盖原有的 Cookie 从而达到删除的效果
            ResponseCookie cookie = ResponseCookie.from("token", "")
                    .httpOnly(true)
                    .path("/")
                    .maxAge(0)
                    .build();

            // 设置 Cookie 到响应头
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
        }




    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (RoleEnum.USER.name().equals(user.getRole())){
            userService.register(user);
        }else{
            return Result.error("您的参数输入错误！");
        }
        return Result.success();
    }

}
