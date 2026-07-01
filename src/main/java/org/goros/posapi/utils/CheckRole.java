package org.goros.posapi.utils;

import org.goros.posapi.model.entity.AppUser;

public class CheckRole {
    public static boolean isSuperAdmin(AppUser user) {
        return user.getRole().getRoleName().equals("SUPER ADMIN");
    }
}