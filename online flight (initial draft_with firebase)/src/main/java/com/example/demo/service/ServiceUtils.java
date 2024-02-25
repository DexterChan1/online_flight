package com.example.demo.service;

import com.example.demo.exception.UnauthorizedOperationException;
import org.springframework.stereotype.Component;

@Component
public class ServiceUtils {
    /*public static void validateSystemUserForEntity(SystemUser expectedUser,
                                                   SystemUser actualUser,
                                                   Object entity) throws UnauthorizedOperationException {
        if (!expectedUser.equals(actualUser)) {
            throw new UnauthorizedOperationException(
                    String.format("Unauthorized operation upon [%s] entity from user [%s]",
                            entity.getClass().getSimpleName(), actualUser.getId())
            );
        }
    } */

    public void validateSystemUserForEntity(String uid, String uid1) {
        if (!uid.equals(uid1)) {
            throw new UnauthorizedOperationException("You do not have permission to modify this entity.");
        }
    }
}
