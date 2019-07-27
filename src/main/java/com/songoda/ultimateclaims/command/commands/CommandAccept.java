package com.songoda.ultimateclaims.command.commands;

import com.songoda.ultimateclaims.UltimateClaims;
import com.songoda.ultimateclaims.command.AbstractCommand;
import com.songoda.ultimateclaims.invite.Invite;
import com.songoda.ultimateclaims.member.ClaimRole;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAccept extends AbstractCommand {

    public CommandAccept(AbstractCommand parent) {
        super("accept", parent, true);
    }

    @Override
    protected ReturnType runCommand(UltimateClaims instance, CommandSender sender, String... args) {
        Player player = (Player) sender;


        Invite invite = instance.getInviteTask().getInvite(player.getUniqueId());

        if (invite == null) {
            instance.getLocale().getMessage("command.accept.none").sendPrefixedMessage(player);
        } else {
            invite.getClaim().addMember(player, ClaimRole.MEMBER);
            invite.accepted();
            instance.getLocale().getMessage("command.accept.success")
                    .processPlaceholder("claim", invite.getClaim().getName())
                    .sendPrefixedMessage(player);

            OfflinePlayer owner = Bukkit.getPlayer(invite.getInviter());

            if (owner != null && owner.isOnline())
                instance.getLocale().getMessage("command.accept.accepted")
                        .processPlaceholder("name", player.getName())
                        .sendPrefixedMessage(owner.getPlayer());
        }

        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "ultimateclaims.accept";
    }

    @Override
    public String getSyntax() {
        return "/ucl invite";
    }

    @Override
    public String getDescription() {
        return "Invite.";
    }
}
