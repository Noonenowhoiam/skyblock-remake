package com.sweattypalms.skyblock.core.mobs.builder;

import java.util.Arrays;

public enum Regions {
    GRAVEYARD,
    END
    ;

    public static Regions getRegion(String region) {
        final Regions[] _region = {null};
        Arrays.stream(Regions.values()).toList().forEach(reg -> {
            if (reg.name().equalsIgnoreCase(region)) {
                _region[0] = reg;
            }
        });
        return _region[0];
    }
}