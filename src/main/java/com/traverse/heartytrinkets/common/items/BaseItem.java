package com.traverse.heartytrinkets.common.items;

import com.traverse.heartytrinkets.common.HeartyTrinkets;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;

public class BaseItem extends Item {

    public BaseItem() {
        super(new FabricItemSettings().group(HeartyTrinkets.TAB));
    }

    public BaseItem(int maxCount) {
        super(new FabricItemSettings().group(HeartyTrinkets.TAB).maxCount(maxCount));
    }

    public BaseItem(int hunger, float saturation) {
        super(new FabricItemSettings().group(HeartyTrinkets.TAB).food(new FoodComponent.Builder().saturationModifier(saturation).hunger(hunger).alwaysEdible().build()));
    }

}
