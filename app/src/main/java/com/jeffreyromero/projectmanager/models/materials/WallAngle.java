package com.jeffreyromero.projectmanager.models.materials;

import com.jeffreyromero.projectmanager.models.Material;

public class WallAngle extends Material {

    public WallAngle() {
        super("Wall Angle");
    }

    public void calcQuantity(double length, double width) {
        double par = (length * 2) + (width * 2);
        super.setQuantity(par / getLength() * getCoefficient());
    }
}
