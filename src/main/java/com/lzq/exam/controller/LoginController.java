package com.lzq.exam.controller;

import com.lzq.exam.common.Role;
import com.lzq.exam.entity.Login;
import com.lzq.exam.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author beastars
 */
@RestController
@RequestMapping("/login")
public class LoginController {
  @Autowired
  private LoginService loginService;

  @PostMapping
  public ResponseEntity<Role> login(@RequestBody Login login) {
    Role body = loginService.login(login.getUsername(), login.getPwd());
    System.out.println(body);
    return ResponseEntity.ok(body);
  }
}
