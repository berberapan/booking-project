package org.example.booking_project.service;

import org.example.booking_project.Dtos.BlacklistedDTO;

public interface BlacklistService {
    public BlacklistedDTO saveUpdateBlacklisted(BlacklistedDTO blacklistedDTO);
    public BlacklistedDTO findBlacklistedByEmail(String email);

}
