package org.example.booking_project.service.impl;

import org.example.booking_project.Dtos.BlacklistedDTO;
import org.example.booking_project.service.BlacklistService;
import org.springframework.stereotype.Service;

@Service
public class BlacklistedServiceImpl implements BlacklistService {
    @Override
    public BlacklistedDTO saveUpdateBlacklisted(BlacklistedDTO blacklistedDTO) {
        return null;
    }

    @Override
    public BlacklistedDTO getBlacklistedByEmail(String email) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }
}
