package com.tool.wxtoolproject.api.tactics.service.impl;

import com.tool.wxtoolproject.api.tactics.service.FlyBehavior;

public class NoFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("不会飞翔");
    }
}
