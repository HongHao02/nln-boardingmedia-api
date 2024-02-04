package com.b2012202.mxhtknt.Services.Impl;

import com.b2012202.mxhtknt.Models.Role;
import com.b2012202.mxhtknt.Repositories.RoleRepository;
import com.b2012202.mxhtknt.Services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Role findByRoleName(String roleName) {
        var role = roleRepository.findByNameRole(roleName);
        return role.orElse(null);
    }
}
