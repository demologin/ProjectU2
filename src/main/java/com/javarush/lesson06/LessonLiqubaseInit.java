package com.javarush.lesson06;

import com.javarush.lesson07.Cnn;
import com.javarush.lesson07.Configuration;
import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;

public class LessonLiqubaseInit {
    public static void main(String[] args) throws Exception {
        System.out.println("Running Liquibase...");

        Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
            CommandScope update = new CommandScope("update");

            update.addArgumentValue("changelogFile", "db/changelog.xml");
            update.addArgumentValue("url", Configuration.getProperty(Cnn.DB_URI));
            update.addArgumentValue("username", Configuration.getProperty(Cnn.DB_USER));
            update.addArgumentValue("password", Configuration.getProperty(Cnn.DB_PASSWORD));

            update.execute();
        });

        System.out.println("Running Liquibase...DONE");
    }
}
