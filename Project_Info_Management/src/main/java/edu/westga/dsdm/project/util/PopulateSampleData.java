package edu.westga.dsdm.project.util;

import edu.westga.dsdm.project.model.AccountManager;
import edu.westga.dsdm.project.model.User;

import java.io.File;

/**
 * Utility class that populates the system with sample users and projects.
 * It creates 5 users with usernames "user1" to "user5", each with 2 projects: Alpha and Beta.
 * 
 * @author Jacob Baker
 * @version Spring 2025
 */
public final class PopulateSampleData {

    private static final String BASE_USERNAME = "user";

    public static void populateSampleData() {
        for (int i = 1; i <= 5; i++) {
            String username = BASE_USERNAME + i;
            String password = "pass" + i;
            String email = username + "@example.com";

            boolean created = AccountManager.createAccount(username, password, email);
            if (!created) {
                continue;
            }

            User user = AccountManager.findUserByUsername(username);
            if (user != null) {
                user.setDescription("Hello, I am " + username + " and I build cool stuff!");

                user.getProjectManager().addProject(
                    "Project Alpha " + i,
                    "This is Project Alpha by " + username,
                    "https://github.com/" + username + "/alpha",
					new File("sample/path/alpha" + i)
                );

                user.getProjectManager().addProject(
                    "Project Beta " + i,
                    "This is Project Beta by " + username,
                    "https://github.com/" + username + "/beta",
                    new File("sample/path/beta" + i)
                );
            }
        }
    }

    private PopulateSampleData() {
        // prevent instantiation
    }
}
