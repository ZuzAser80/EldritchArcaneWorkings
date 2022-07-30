package net.fabricmc.example.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.example.item.AbstractMagicRodItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class EAWEvents {

    //public static KeyBinding switchSpell1;
    //
    //public static void registerSwitchSpell1Keybinding() {
    //
    //}
    //
    //public static void SpellSwitch1S2CPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender sender) {
    //    server.execute(() -> {
    //        PlayerInventory i = player.getInventory();
    //        if(i.getMainHandStack().getItem() instanceof AbstractMagicRodItem rodItem) {
    //            rodItem.switchCurrentSpell();
    //        }
    //    });
    //}
    //
    //public static Packet<?> createSpellSwitch1S2CPacket() {
    //    PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
    //    return ClientPlayNetworking.createC2SPacket(new Identifier("eaw", "switch_spell_1_packet"), buffer);
    //}
}
