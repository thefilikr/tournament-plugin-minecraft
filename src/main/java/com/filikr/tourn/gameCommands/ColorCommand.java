package com.filikr.tourn;

public class ColorCommand {
    private int r;
    private int g;
    private int b;


    public ColorCommand(int r, int g, int b) {
        setColor(r, g, b);
    }


    public void setColor(int r, int g, int b) {
        int[] rgb = {r, g, b};

        for (int i = 0; i<3; i++) {
            if(rgb[i] < 0) rgb[i] = 0;
            if(rgb[i] > 255) rgb[i] = 255;
        }

        this.r = rgb[0];
        this.g = rgb[1];
        this.b = rgb[2];
    }

    public int[] getColor() {
        return new int[]{r, g, b};
    }


}
