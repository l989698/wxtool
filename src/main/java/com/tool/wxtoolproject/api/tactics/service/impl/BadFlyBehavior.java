package com.tool.wxtoolproject.api.tactics.service.impl;

import com.tool.wxtoolproject.api.tactics.service.FlyBehavior;

public class BadFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("飞翔技术一般");
    }
}
