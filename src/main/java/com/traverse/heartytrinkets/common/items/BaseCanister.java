package com.traverse.heartytrinkets.common.items;

import com.traverse.heartytrinkets.common.util.HeartType;

public class BaseCanister extends BaseItem {

    public HeartType type;

    public BaseCanister(int maxCount, HeartType type) {
        super(maxCount);
        this.type = type;
    }
}


