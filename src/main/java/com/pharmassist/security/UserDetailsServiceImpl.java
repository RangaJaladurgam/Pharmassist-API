package com.pharmassist.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.pharmassist.repository.AdminRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final AdminRepository adminRepository;
	
	public UserDetailsServiceImpl(AdminRepository adminRepository) {
		super();
		this.adminRepository = adminRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return adminRepository.findByEmail(username)
								.map(UserDetailsImpl::new)
								.orElseThrow(()-> new UsernameNotFoundException("failed to Authenticate admin"));
	}

}
