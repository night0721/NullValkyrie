package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.Main;

public class CommandManager {
    private Main main;
    public CommandManager(Main main) {
        this.main = main;
    }
    public void register() {
        new VanishCommand();
        new TestCommand();
        new AnvilCommand();
        new ArmorCommand();
        new MenuCommand();
        new MessageCommand();
        new HologramCommand();
        new CraftCommand();
        new EnchantingCommand();
        new BetaCommand(main);
        new RankCommand(main);
        new UtilCommand(main);
        new WeaponCommand(main);
        new MinerCommand(main);
    }
}
