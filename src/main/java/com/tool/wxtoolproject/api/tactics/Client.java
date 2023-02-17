package com.tool.wxtoolproject.api.tactics;

import com.tool.wxtoolproject.api.tactics.dto.Duck;
import com.tool.wxtoolproject.api.tactics.dto.PekingDuck;
import com.tool.wxtoolproject.api.tactics.dto.ToyDuck;
import com.tool.wxtoolproject.api.tactics.dto.WildDuck;
import com.tool.wxtoolproject.api.tactics.service.impl.GoodFlyBehavior;

public class Client {

    public static void main(String[] args) {
        PekingDuck pekingDuck = new  PekingDuck();
        pekingDuck.display();
        pekingDuck.fly();

        ToyDuck toyDuck = new ToyDuck();
        toyDuck.display();
        toyDuck.fly();

        WildDuck wildDuck = new WildDuck();
        wildDuck.display();
        wildDuck.fly();



        toyDuck.setFlyBehavior(new GoodFlyBehavior());
        toyDuck.display();
        toyDuck.fly();

        Duck duck  =new PekingDuck();
        duck.display();
        duck.fly();
    }

}
