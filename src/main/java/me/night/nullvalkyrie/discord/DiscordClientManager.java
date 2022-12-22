package me.night.nullvalkyrie.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;


public class DiscordClientManager {

    public DiscordClientManager() {
        register();
    }

    public void register() {
        JDABuilder builder = JDABuilder.createDefault(System.getenv("DISCORD_TOKEN"));
        builder.setActivity(Activity.streaming("cath.exe", "https://www.youtube.com/watch?v=YSKDu1gKntY"));
        try {
            JDA jda = builder.build();
            System.out.println(jda.getSelfUser().getName());
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}
