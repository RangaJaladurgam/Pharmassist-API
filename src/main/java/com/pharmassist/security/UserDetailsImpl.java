package com.pharmassist.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pharmassist.entity.Admin;

public class UserDetailsImpl implements UserDetails {
	private final Admin admin;
	
	public UserDetailsImpl(Admin admin) {
		this.admin = admin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return admin.getPassword();
	}

	@Override
	public String getUsername() {
		return admin.getEmail();
	}

}
