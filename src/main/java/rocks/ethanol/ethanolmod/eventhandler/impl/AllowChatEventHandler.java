package rocks.ethanol.ethanolmod.eventhandler.impl;

import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import rocks.ethanol.ethanolmod.EthanolMod;
import rocks.ethanol.ethanolmod.networking.impl.serverbound.ServerboundCommandPayload;
import rocks.ethanol.ethanolmod.structure.MinecraftWrapper;

public class AllowChatEventHandler implements ClientSendMessageEvents.AllowChat, MinecraftWrapper {

    @Override
    public final boolean allowSendChatMessage(final String message) {
        final EthanolMod ethanolMod = EthanolMod.getInstance();
        final String prefix = ethanolMod.getConfiguration().getCommandPrefix();

        if (!ethanolMod.isInstalled() || !message.startsWith(prefix)) {
            return true;
        }

        final String command = message.substring(prefix.length());
        if (command.isEmpty()) {
            return false;
        }

        this.mc.inGameHud.getChatHud().addToMessageHistory(message);
        this.mc.getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(new ServerboundCommandPayload(command)));
        return false;
    }

}
