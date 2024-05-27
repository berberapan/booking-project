package org.example.booking_project;

import jakarta.servlet.http.HttpServletRequest;

public class Utils {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
