package com.example.msusers.repository;

import com.example.msusers.domain.User;
import com.example.msusers.domain.dto.UserRegistrationDto;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class KeyCloakUserRepository implements UserRepository{

    @Autowired
    private Keycloak keycloak;

    @Value("${final.keycloak.realm}")
    private String realm;
    @Override
    public User findById(String id) {return toUser(keycloak.realm(realm).users().get(id).toRepresentation());}
    @Override
    public List<User> findAll(){return keycloak.realm(realm).users().list().stream().map(this::toUser).toList();}

    @Override
    public User saveUserProvider(UserRegistrationDto user) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(user.toRepresentation());
        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = usersResource.get(userId);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(user.getPassword());
        userResource.resetPassword(passwordCred);

        ClientRepresentation billsClient = realmResource.clients().findByClientId("bills-client").get(0);
        RoleRepresentation userClientRole = realmResource.clients().get(billsClient.getId()).roles().get("USER").toRepresentation();
        userResource.roles().clientLevel(billsClient.getId()).add(Arrays.asList(userClientRole));

        GroupRepresentation providersRepresentation = realmResource.getGroupByPath("PROVIDERS");
        userResource.joinGroup(providersRepresentation.getId());

        return toUser(userResource.toRepresentation());
    }


    private User toUser(UserRepresentation userRepresentation){
        return new User(userRepresentation.getId(), userRepresentation.getUsername(), userRepresentation.getEmail(), userRepresentation.getFirstName(), null);
    }
}
