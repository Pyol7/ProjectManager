package com.jeffreyromero.projectmanager.models.materials;

import com.jeffreyromero.projectmanager.models.Material;

/**
 * All dropped ceiling tees are derived from this class
 * Notes:
 * Main tee spacing = Secondary tee length,
 * Secondary tee spacing = Tertiary tee length,
 * Tertiary tee spacing = Secondary tee length.
 */
public class Tee extends Material {

    private static final int MAIN_TEE_MIN_LENGTH = 120;
    private static final int MAIN_TEE_MAX_LENGTH = 144;
    private static final int SECONDARY_TEE_LENGTH = 48;
    private static final int TERTIARY_TEE_LENGTH = 24;


    public Tee() {
        super("Tee");
    }

    public void calcQuantity(double length, double width) {
        double q;
        // Determine type of tee
        if (getLength() >= MAIN_TEE_MIN_LENGTH && getLength() <= MAIN_TEE_MAX_LENGTH){
            // Calculate quantity for main tee
            q = (length / SECONDARY_TEE_LENGTH) * (width / getLength());
        } else if (getLength() == SECONDARY_TEE_LENGTH){
            // Calculate quantity for Secondary Tee
            q = (length / getLength()) * (width / TERTIARY_TEE_LENGTH);
        } else if (getLength() == TERTIARY_TEE_LENGTH){
            // Calculate quantity for Tertiary Tee
            q = (width / getLength()) * (length / SECONDARY_TEE_LENGTH);
        } else {
            throw new IllegalArgumentException("Unrecognized Tee length");
        }
        super.setQuantity(q * getCoefficient());
    }
}
