package me.night.nullvalkyrie.discord;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;


public class DiscordClientManager {
    private JDA jda;
    public DiscordClientManager() {
        register();
    }
    public void register() {
        JDABuilder builder = JDABuilder.createDefault("OTk3ODczMzgyNjM0ODM2MDQ5.Gd9Fdh.GEOleI-1znVeHwaAef54nXl2ovlSmC3hQX-qQI");
        builder.setActivity(Activity.streaming("cath.exe", "https://www.youtube.com/watch?v=YSKDu1gKntY"));
        try {
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}
