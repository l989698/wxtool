package com.tool.wxtoolproject.api.tactics.dto;

import com.tool.wxtoolproject.api.tactics.service.FlyBehavior;

public abstract class Duck {

    // 属性,策略接口
    FlyBehavior flyBehavior;

    public Duck() {}

    // 显示鸭子信息
    public abstract void display();

    public void fly() {
        if (flyBehavior != null) {
            flyBehavior.fly();
        }
    }
    // 方便扩展
    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }
}
