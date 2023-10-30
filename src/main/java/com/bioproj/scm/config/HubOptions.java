package com.bioproj.scm.config;

import lombok.extern.slf4j.Slf4j;

import java.io.Console;

@Slf4j
public class HubOptions {
    String hubProvider;

    String hubUser;

    /**
     * Return the password provided on the command line or stop allowing the user to enter it on the console
     *
     * @return The password entered or {@code null} if no user has been entered
     */
    String getHubPassword() {

        if( hubUser==null )
            return null;

        int p = hubUser.indexOf(':');
        if( p != -1 )
            return hubUser.substring(p+1);

        Console console = System.console();
        if( console!=null )
            return null;

        log.info("Enter your $hubProvider password: ");
        char[] pwd = console.readPassword();
        return new String(pwd);
    }


    String getHubUser() {
        if(hubUser!=null) {
            return hubUser;
        }

        int p = hubUser.indexOf(':');
        return p != -1 ? hubUser.substring(0,p) : hubUser;
    }

}
