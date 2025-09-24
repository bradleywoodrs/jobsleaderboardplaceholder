package com.github.bradleywoodrs.jobsleaderboardplaceholder;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JobsExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "jobs";
    }

    @Override
    public @NotNull String getAuthor() {
        return "bradleywoodrs";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (!(params.startsWith("toplevel_levels_") || params.startsWith("toplevel_name_"))) return null;

        String[] parts = params.split("_");
        if (parts.length != 3) return "";

        int pos;
        try {
            pos = Integer.parseInt(parts[2]) - 1;
        } catch (NumberFormatException e) {
            return "";
        }

        if (pos < 0) return "";

        List<JobEntry> allJobs = new ArrayList<>();
        for (JobsPlayer jPlayer : Jobs.getPlayerManager().getPlayersCache().values()) {
            for (JobProgression jp : jPlayer.getJobProgression()) {
                allJobs.add(new JobEntry(jPlayer, jp));
            }
        }

        allJobs.sort(Comparator.comparingInt((JobEntry je) -> je.progression.getLevel()).reversed());

        if (pos >= allJobs.size()) return "";

        JobEntry entry = allJobs.get(pos);
        if (params.startsWith("toplevel_levels_")) {
            return entry.progression.getJob().getDisplayName() + " " + entry.progression.getLevel();
        } else {
            return entry.progression.getJob().getChatColor() + entry.player.getUserName();
        }
    }

    private static class JobEntry {
        final JobsPlayer player;
        final JobProgression progression;

        JobEntry(JobsPlayer player, JobProgression progression) {
            this.player = player;
            this.progression = progression;
        }
    }
}
