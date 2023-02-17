package com.tool.wxtoolproject.api.tactics.dto;

import com.tool.wxtoolproject.api.tactics.service.impl.GoodFlyBehavior;

public class WildDuck extends Duck{

    // 构造器传入FlyBehavior策略对象
    public WildDuck() {
        flyBehavior = new GoodFlyBehavior();
    }

    @Override
    public void display() {
        System.out.println("这是野鸭");
    }
}
