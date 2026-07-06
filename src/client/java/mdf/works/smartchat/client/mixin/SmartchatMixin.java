package mdf.works.smartchat.client.mixin;

import mdf.works.smartchat.client.ReceveChatCallBack;
import net.minecraft.Optionull;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessageSource;import net.minecraft.client.multiplayer.chat.GuiMessageTag;import net.minecraft.network.chat.Component;import net.minecraft.network.chat.MessageSignature;import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class SmartchatMixin {


    @Inject(at = @At("TAIL"), method = "addMessage")
    private void test(Component contents, MessageSignature signature, GuiMessageSource source, GuiMessageTag tag, CallbackInfo ci) {
        String string2 = Optionull.map(tag, GuiMessageTag::logTag);
        if(string2 == null){
            string2 = "PLChat";
        }
        ReceveChatCallBack.EVENT.invoker().interact(contents.getString(),string2);
    }
}