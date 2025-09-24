package com.github.bradleywoodrs.jobsleaderboardplaceholder;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public final class Jobsleaderboardplaceholder extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new JobsExpansion().register();
        }
    }
}
