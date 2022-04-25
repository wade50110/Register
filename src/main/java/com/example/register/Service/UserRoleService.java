package com.example.register.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.example.register.entity.AppUser;
import com.example.register.entity.Role;
import com.example.register.entity.UserRole;
import com.example.register.repository.AppUserRepository;
import com.example.register.repository.RoleReposity;
import com.example.register.repository.UserRoleReposity;

@Service
@DependsOn({"appUserService", "roleService"})
public class UserRoleService {

	@Autowired
    private  UserRoleReposity userRoleRepo;
	
	@Autowired
    private  AppUserRepository userRepo;
	
	@Autowired
    private  RoleReposity roleRepo;
	
	
	public void insertUserRole(String username) {
		Optional<AppUser> user = userRepo.findByUsername(username);
		Optional<Role> role = roleRepo.findByroleName("HR_STAFF");
		List<UserRole> userRoleList = new ArrayList<>();
        setUserRoleList(userRoleList, user.get().getId(), role.get().getId());
		userRoleRepo.saveAll(userRoleList);
	}
	
    public List<UserRole> getByUserId(Long userId) {
        return userRoleRepo.findByUserId(userId);
    }
    
    private void setUserRoleList(List<UserRole> userRoleList, Long userId, Long roleId) {
        UserRole userRole = UserRole.builder().userId(userId).roleId(roleId).build();
        userRoleList.add(userRole);
    }


}
