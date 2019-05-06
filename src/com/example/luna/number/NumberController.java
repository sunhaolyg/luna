package com.example.luna.number;

import java.util.List;

public class NumberController {

    public static boolean isSuccess(List<NumberEntity> entities) {
        for (NumberEntity entity : entities) {
            if (entity.getPosition() != entity.getNumber()) {
                return false;
            }
        }
        return true;
    }

}
