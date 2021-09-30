package dbryzz.services.auth.model;

import dbryzz.services.auth.constant.RoleName;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "role")
@Data
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @NaturalId
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleName role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> userList = new HashSet<>();

}
