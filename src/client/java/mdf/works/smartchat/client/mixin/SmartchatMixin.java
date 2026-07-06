package mdf.works.smartchat.client.mixin;

import mdf.works.smartchat.client.ReceveChatCallBack;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class SmartchatMixin {
    @Inject(at = @At("TAIL"), method = "addMessage")
    private void ReceveChatEvent(Component contents, MessageSignature signature, GuiMessageTag tag, CallbackInfo ci) {
        ReceveChatCallBack.EVENT.invoker().interact(contents.getString());
    }
}