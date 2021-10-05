package dbryzz.services.auth.service.impl;

import dbryzz.services.auth.service.AuthUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Override
    public Long getAuthUserId(){
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
