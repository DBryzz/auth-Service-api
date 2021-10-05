package dbryzz.services.auth.service;

import dbryzz.services.auth.model.Role;

import java.util.Collection;

public interface RoleService {

    Collection<Role> findAll();
}
