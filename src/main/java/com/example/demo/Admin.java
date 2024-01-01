package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("secured")
public class Admin
{
  @GetMapping("admin")
  public String akljf() {
	  return "welcomr yo admin page";
  }
}
