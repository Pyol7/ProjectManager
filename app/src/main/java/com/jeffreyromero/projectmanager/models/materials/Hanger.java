package com.jeffreyromero.projectmanager.models.materials;

import com.jeffreyromero.projectmanager.models.Material;

public class Hanger extends Material {

    public Hanger() {
        super("Hanger");
    }

    public void calcQuantity(double length, double width) {
        double q = Math.ceil(
                (length / getSpacing() - 1 ) * (width / getSpacing() - 1)) * getCoefficient();
        super.setQuantity((q%2 != 0) ? q+1 : q);
    }
}
