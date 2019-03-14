package com.jeffreyromero.projectmanager.models.materials;

import com.jeffreyromero.projectmanager.models.Material;

public class CeilingTile extends Material {

    public CeilingTile() {
        super("Ceiling Tile");
    }

    public void calcQuantity(double length, double width) {
        super.setQuantity((length * width)/(getLength() * getWidth()) * getCoefficient());
    }
}
