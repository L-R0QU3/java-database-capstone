package com.smartcare.controller;

import com.smartcare.service.TokenValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private TokenValidationService tokenValidationService;

    // =====================================================
    // Token-based routes (per lab requirements)
    // =====================================================

    @GetMapping("/adminDashboard/{token}")
    public String adminDashboardWithToken(@PathVariable String token) {
        Map<String, String> result = tokenValidationService.validateToken(token, "admin");
        if (result.isEmpty()) {
            return "admin/adminDashboard";
        }
        return "redirect:/";
    }

    @GetMapping("/doctorDashboard/{token}")
    public String doctorDashboardWithToken(@PathVariable String token) {
        Map<String, String> result = tokenValidationService.validateToken(token, "doctor");
        if (result.isEmpty()) {
            return "doctor/doctorDashboard";
        }
        return "redirect:/";
    }

    // =====================================================
    // Direct routes (used by our frontend)
    // =====================================================

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/adminDashboard";
    }

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard() {
        return "doctor/doctorDashboard";
    }
}
