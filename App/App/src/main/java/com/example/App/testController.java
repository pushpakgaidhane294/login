package com.example.App;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class testController {

@Autowired
private JdbcTemplate jdbcTemplate;

@GetMapping("/run")
public String run() {
return "NewFile"; // login page
}

@GetMapping("/register")
public String registerPage() {
return "register";
}

@PostMapping("/register")
public String registerUser(@RequestParam String username,
@RequestParam String password,
RedirectAttributes redirectAttributes) {

// Check if username already exists
String checkSql = "SELECT COUNT(*) FROM users WHERE username = ?";
Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, username);

if (count != null && count > 0) {
redirectAttributes.addFlashAttribute("error", "Username already exists");
return "redirect:/register";
}

// Insert new user
String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
jdbcTemplate.update(insertSql, username, password);

redirectAttributes.addFlashAttribute("message", "Registration successful! Please login.");
return "redirect:/run";
}


@PostMapping("/submit")
public String login(@RequestParam String username,
@RequestParam String password,
RedirectAttributes redirectAttributes) {

String sql = "SELECT COUNT(*) FROM users WHERE username=? AND password=?";
Integer count = jdbcTemplate.queryForObject(
sql,
Integer.class,
username,
password
);

if (count != null && count > 0) {
redirectAttributes.addFlashAttribute("name", username);
return "redirect:/success";
} else {
redirectAttributes.addFlashAttribute("error", "Invalid username or password");
return "redirect:/run";
}
}

@GetMapping("/success")
public String success() {
return "success";
}

@PostMapping("/logout")
public String logout(HttpSession session) {
session.invalidate(); // clear session
return "redirect:/run"; // back to login
}
}