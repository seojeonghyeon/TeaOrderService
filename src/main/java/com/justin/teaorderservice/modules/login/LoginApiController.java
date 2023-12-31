package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.infra.auth.JwtTokenProvider;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.infra.exception.LoginException;
import com.justin.teaorderservice.modules.vo.RequestLogin;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Order Login API Controller",
        description = "Order Login API Controller"
)
@Slf4j
@RestController
@RequestMapping("/api/order/login")
@RequiredArgsConstructor
public class LoginApiController {

    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<String> login(final @RequestBody @Validated RequestLogin requestLogin) {

        String memberId = loginService.login(requestLogin.getEmail(), requestLogin.getPassword());

        if(memberId == null){
            throw new LoginException(ErrorCode.LOGIN_FAIL);
        }

        String token = jwtTokenProvider.createToken(memberId);
        log.info(token);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
