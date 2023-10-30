package com.bioproj.scm.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Const {
    static public final String APP_NAME = "proj";
    static public final Path APP_HOME_DIR = getHomeDir(APP_NAME);



    private static Path getHomeDir(String appname) {
        Path result = Paths.get(System.getProperty("user.home")).resolve(appname);
        if( !result.toFile().exists() && !result.toFile().mkdir() ) {
            throw new IllegalStateException("Cannot create path '${result}' -- check file system access permission")
        }

        return result;
    }

}
