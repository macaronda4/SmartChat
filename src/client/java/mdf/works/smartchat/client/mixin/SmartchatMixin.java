package mdf.works.smartchat.client.mixin;

import mdf.works.smartchat.client.ReceveChatCallBack;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class SmartchatMixin {
    @Inject(at = @At("HEAD"), method = "addMessageToQueue")
    private void receivechat(GuiMessage guiMessage, CallbackInfo ci) {
        ReceveChatCallBack.EVENT.invoker().interact(guiMessage.content().getString());
    }
}