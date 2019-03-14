package com.jeffreyromero.projectmanager.models.materials;

import com.jeffreyromero.projectmanager.models.Material;

public class WallAngleFastener extends Material {

    public WallAngleFastener() {
        super("Wall Angle Fastener");
    }

    public void calcQuantity(double length, double width) {
        double par = (length * 2) + (width * 2);
        super.setQuantity(par / getSpacing() * getCoefficient());
    }
}
