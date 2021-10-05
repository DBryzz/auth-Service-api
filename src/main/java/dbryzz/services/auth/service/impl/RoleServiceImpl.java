package dbryzz.services.auth.service.impl;

import dbryzz.services.auth.model.Role;
import dbryzz.services.auth.repository.RoleRepository;
import dbryzz.services.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Find all roles from the database
     */
    @Override
    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }
}
