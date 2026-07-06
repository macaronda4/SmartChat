package mdf.works.smartchat.client.mixin;

import mdf.works.smartchat.client.ReceveChatCallBack;
import mdf.works.smartchat.client.SmartchatClient;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessageSource;import net.minecraft.client.multiplayer.chat.GuiMessageTag;import net.minecraft.network.chat.Component;import net.minecraft.network.chat.MessageSignature;import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class SmartchatMixin {


    @Inject(at = @At("TAIL"), method = "addMessage")
    private void test(Component contents, MessageSignature signature, GuiMessageSource source, GuiMessageTag tag, CallbackInfo ci) {
        ReceveChatCallBack.EVENT.invoker().interact(contents.getString());
    }
}