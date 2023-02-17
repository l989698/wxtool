package com.tool.wxtoolproject.api.tactics.dto;

import com.tool.wxtoolproject.api.tactics.service.impl.BadFlyBehavior;

public class PekingDuck extends Duck{

    public PekingDuck() {
        flyBehavior = new BadFlyBehavior();
    }

    @Override
    public void display() {
        System.out.println("这是北京鸭");
    }
}
