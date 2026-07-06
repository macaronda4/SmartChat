package mdf.works.smartchat.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;

public interface ReceveChatCallBack{
    Event<ReceveChatCallBack> EVENT = EventFactory.createArrayBacked(ReceveChatCallBack.class,
        (listeners) -> (Chatmsg, MessageTag) ->{
            for (ReceveChatCallBack listener: listeners){
                InteractionResult result = listener.interact(Chatmsg, MessageTag);
                if (result != InteractionResult.PASS) {
                    return result;
                }
            }
            return InteractionResult.PASS;
        });

    InteractionResult interact(String chatmsg, String MessageTag);
}
