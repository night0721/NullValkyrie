package me.night.nullvalkyrie.commands;

import me.night.nullvalkyrie.Main;

public class CommandManager {
    public CommandManager(Main main) {
        new VanishCommand();
        new TestCommand();
        new AnvilCommand();
        new ArmorCommand();
        new MenuCommand();
        new MessageCommand();
        new HologramCommand();
        new CraftCommand();
        new EnchantingCommand();
        new WeaponCommand();
        new RankCommand();
        new UtilCommand();
        new ShopCommand();
        new BetaCommand();
        new MinerCommand(main);
    }
}
