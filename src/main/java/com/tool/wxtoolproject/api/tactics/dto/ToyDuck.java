package com.tool.wxtoolproject.api.tactics.dto;

import com.tool.wxtoolproject.api.tactics.service.impl.NoFlyBehavior;

public class ToyDuck extends Duck{

    public ToyDuck() {
        flyBehavior = new NoFlyBehavior();
    }

    @Override
    public void display() {
        System.out.println("这是玩具鸭");
    }
}
