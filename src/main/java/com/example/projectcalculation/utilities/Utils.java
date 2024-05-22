package com.example.projectcalculation.utilities;

import com.example.projectcalculation.model.AccountModel;
import com.example.projectcalculation.model.PermissionLevel;
import jakarta.servlet.http.HttpSession;

public class Utils {
    public static boolean validSession(HttpSession session) {
        if (session.getAttribute(Constant.CURRENT_USER) != null) return true;
        return false;
    }

    public static boolean validAdmin(HttpSession session) {
        if (((AccountModel)session.getAttribute(Constant.CURRENT_USER)).getPermissionLevel() == PermissionLevel.ADMINISTRATOR) return true;
        return false;
    }
}

