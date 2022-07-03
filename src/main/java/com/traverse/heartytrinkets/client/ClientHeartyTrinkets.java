package com.traverse.heartytrinkets.client;

import com.traverse.heartytrinkets.client.screen.CanisterBeltGui;
import com.traverse.heartytrinkets.common.HeartyTrinkets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class ClientHeartyTrinkets implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(HeartyTrinkets.CANISTER_BELT_CONTAINER, CanisterBeltGui::new);
      /*  ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier(HeartyTrinkets.MOD_ID, "canister_belt"), (syncId, identifier, player, buf) -> {
            CanisterBeltContainer container = new CanisterBeltContainer(syncId, player);
            return new CanisterBeltGui(container, player.getInventory());
        }); */
    }
}
