package de.thzockt.thUtils.APIs.TimerAPI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TimerColorAPI {

    public static Component colorize(String string, ColorType type, String color1, String color2, UUID instanceUUID, int speed, int difference) {
        int time = 0;
        if (type != ColorType.SOLID && type != ColorType.SOLID_CUSTOM) {
            time = (int) TimerAPI.getTime(instanceUUID);
        }
        switch (type) {
            case SOLID -> {
                return solid(string, color1);
            }
            case RGB_TRANSITION -> {
                return rgb_transition(string, time, speed, difference);
            }
            case RGB_FADE -> {
                return rgb_fade(string, time, speed);
            }
            case SOLID_CUSTOM -> {
                return solid_custom(string, color1);
            }
            case TRANSITION -> {
                return transition(string, color1, color2, time, speed, difference);
            }
            case FADE -> {
                return fade(string, color1, color2, time, speed);
            }
            default -> {
                return solid(string, "f");
            }
        }
    }

    private static Component solid(String string, String color) {
        if (isColor(color)) {
            TextColor realColor = convertColorCodeToColor(color);
            return Component.text(string, realColor);
        } else {
            return Component.text(string);
        }
    }

    private static Component rgb_transition(String string, int time, int speed, int difference) {
        Component rgbComponent = Component.text("");
        for (int i = 0; i < string.length(); i++) {
            int frame = ((time + (i * difference)) / speed) % 1536;
            Integer[] color = getRGBColorByFrame(frame);
            int red = color[0];
            int green = color[1];
            int blue = color[2];
            TextColor rgbColor = TextColor.color(red, green, blue);
            char letter = string.charAt(i);
            rgbComponent = rgbComponent.append(Component.text(letter, rgbColor));
        }
        return rgbComponent;
    }

    private static Component rgb_fade(String string, int time, int speed) {
        int frame = (time / speed) % 1536;

        Integer[] color = getRGBColorByFrame(frame);
        int red = color[0];
        int green = color[1];
        int blue = color[2];

        TextColor rgbColor = TextColor.color(red, green, blue);
        return Component.text(string, rgbColor);
    }

    private static Component solid_custom(String string, String color) {
        int r = Integer.parseInt(color.split(",")[0]);
        int g = Integer.parseInt(color.split(",")[1]);
        int b = Integer.parseInt(color.split(",")[2]);
        TextColor custom_color = TextColor.color(r, g, b);
        return Component.text(string, custom_color);
    }

    private static Component transition(String string, String color1, String color2, int time, int speed, int difference) {
        return Component.text("TRANSITION: " + string);
    }

    private static Component fade(String string, String color1, String color2, int time, int speed) {
        int frame = (time / speed) % 1536;

        int r1 = Integer.parseInt(color1.split(",")[0]);
        int g1 = Integer.parseInt(color1.split(",")[1]);
        int b1 = Integer.parseInt(color1.split(",")[2]);

        int r2 = Integer.parseInt(color2.split(",")[0]);
        int g2 = Integer.parseInt(color2.split(",")[1]);
        int b2 = Integer.parseInt(color2.split(",")[2]);

        //Integer[] color = getColorByFrame(frame, r1, g1, b1, r2, g2, b2);
        return Component.text("FADE: " + string);
    }

    private static boolean isColor(String color) {
        List<String> colors = new ArrayList<>();
        colors.add("1");
        colors.add("2");
        colors.add("3");
        colors.add("4");
        colors.add("5");
        colors.add("6");
        colors.add("7");
        colors.add("8");
        colors.add("9");
        colors.add("0");
        colors.add("a");
        colors.add("b");
        colors.add("c");
        colors.add("d");
        colors.add("e");
        colors.add("f");
        return colors.contains(color);
    }

    /*private static Integer[] getColorByFrame(int frame, int red1, int green1, int blue1, int red2, int green2, int blue2) {
        int red = 0;
        int green = 0;
        int blue = 0;
        if (frame >= 0 && frame <= 512) {
            red = red1;
            green = green1;
            blue = blue1;
        }
        if (frame >= 513 && frame <= 768) {

        }
        if (frame >= 768 && frame <= 1024) {

        }
    }*/

    private static Integer[] getRGBColorByFrame(int frame) {
        int red = 0;
        int green = 0;
        int blue = 0;
        if (frame >= 0 && frame <= 255) {
            red = 255;
            green = frame;
        }
        if (frame >= 256 && frame <= 512) {
            red = 256 - (frame - 256);
            green = 255;
        }
        if (frame >= 513 && frame <= 768) {
            green = 255;
            blue = frame - 513;
        }
        if (frame >= 769 && frame <= 1024) {
            green = 256 - (frame - 768);
            blue = 255;
        }
        if (frame >= 1025 && frame <= 1280) {
            red = frame - 1025;
            blue = 255;
        }
        if (frame >= 1281) {
            red = 255;
            blue = 256 - (frame - 1280);
        }
        if (red > 255) {
            red = 255;
        }
        if (green > 255) {
            green = 255;
        }
        if (blue > 255) {
            blue = 255;
        }
        if (red < 0) {
            red = 0;
        }
        if (green < 0) {
            green = 0;
        }
        if (blue < 0) {
            blue = 0;
        }
        if (red == 0 && green == 0 && blue == 0) {
            red = 255;
        }
        return new Integer[] {red, green, blue};
    }

    public static TextColor convertColorCodeToColor(String code) {
        switch (code) {
            case "0" -> {
                return TextColor.color(0, 0, 0);
            }
            case "1" -> {
                return TextColor.color(0, 0, 170);
            }
            case "2" -> {
                return TextColor.color(0, 170, 0);
            }
            case "3" -> {
                return TextColor.color(0, 170, 170);
            }
            case "4" -> {
                return TextColor.color(170, 0, 0);
            }
            case "5" -> {
                return TextColor.color(170, 0, 170);
            }
            case "6" -> {
                return TextColor.color(255, 170, 0);
            }
            case "7" -> {
                return TextColor.color(170, 170, 170);
            }
            case "8" -> {
                return TextColor.color(85, 85, 85);
            }
            case "9" -> {
                return TextColor.color(85, 85, 255);
            }
            case "a" -> {
                return TextColor.color(85, 255, 85);
            }
            case "b" -> {
                return TextColor.color(85, 255, 255);
            }
            case "c" -> {
                return TextColor.color(255, 85, 85);
            }
            case "d" -> {
                return TextColor.color(255, 85, 255);
            }
            case "e" -> {
                return TextColor.color(255, 255, 85);
            }
            default -> {
                return TextColor.color(255, 255, 255);
            }
        }
    }

}