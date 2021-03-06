package com.example.register.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.register.entity.Role;
import com.example.register.repository.RoleReposity;

@Service
public class RoleService {

	@Autowired
    private RoleReposity roleReposity;

//    @PostConstruct
//    void init() {
//        List<Role> roleList = List.of(
//                Role.builder().roleName("PRODUCT_MANAGER").build(),
//                Role.builder().roleName("HR_MANAGER").build(),
//                Role.builder().roleName("PRODUCT_STAFF").build(),
//                Role.builder().roleName("HR_STAFF").build());
//
//        roleReposity.saveAll(roleList);
//    }

    public Optional<Role> getById(Long id) {
        return roleReposity.findById(id);
    }

    public List<String> getAllRoleNames() {
        return roleReposity.findAll().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());
    }

}
