package de.thzockt.thUtils.APIs.TimerAPI;

import de.thzockt.thUtils.APIs.LanguageAPI.Language;
import de.thzockt.thUtils.APIs.MessageAPI.MessageAPI;
import de.thzockt.thUtils.APIs.MessageAPI.MessageID;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TimerStyles {

    // these letters are being replaced to the numbers
    // v = vanish
    // d = days
    // h = hours
    // m = minutes
    // s = seconds
    // q = milliseconds
    //
    // double letters mean that there is a "0" in front of the number when it is smaller than 10
    // the "v" means that if the number is 0, then the number and everything until the next "{" will vanish as the letter says. The "{v}" MUST be behind the number to actually work
    // the "ddd" means that if the number in front of it is 1 then it will be "day" in the corresponding language and if it is 2 or more than it will be "days"
    //
    // {v}, {d}, {dd}, {ddd}, {h}, {hh}, {m}, {mm}, {s}, {ss}, {q}, {qq}

    public static String getTimer(UUID instanceUUID, Style styleID, long time, Language language) {
        if (styleID.equals(Style.CUSTOM))
            return null;

        return insertNumbers(vanishZeros(getStyle(styleID), time), time, language);
    }

    public static Component bold(Component component, UUID instanceUUID) {
        if (TimerAPI.getBold(instanceUUID)) {
            return component.decoration(TextDecoration.BOLD, true);
        } else {
            return component;
        }
    }

    public static String vanishZeros(String style, long time) {
        List<Integer> vPositions = new ArrayList<>();
        for (int i = 0; i < style.length(); i++) {
            String letter = String.valueOf(style.charAt(i));
            if (letter.equals("v")) {
                if (i > 0 && i < style.length() - 1) {
                    String firstBracket = String.valueOf(style.charAt(i - 1));
                    String secondBracket = String.valueOf(style.charAt(i + 1));
                    if (firstBracket.equals("{") && secondBracket.equals("}")) {
                        if (i > 3) {
                            vPositions.add(i);
                        }
                    }
                }
            }
        }
        String withRemoval = style;
        for (int pos : vPositions) {
            String format = String.valueOf(style.charAt(pos - 3));
            long number = getNumber(format, time);
            if (number != 0) {
                String temp = "";
                for (int i = 0; i < withRemoval.length(); i++) {
                    if (i >= (pos - 1) && i <= (pos + 1)) {
                        temp = temp + "$";
                    } else {
                        temp = temp + withRemoval.charAt(i);
                    }
                }
                withRemoval = temp;
            } else {
                int min = -1;
                int max = -1;
                for (int i = (pos -2); i >= 0; i--) {
                    String letter = String.valueOf(withRemoval.charAt(i));
                    if (letter.equals("{") && min == -1) {
                        min = i;
                    }
                }
                for (int i = (pos + 2); i < withRemoval.length(); i++) {
                    String letter = String.valueOf(withRemoval.charAt(i));
                    if (letter.equals("{") && max == -1) {
                        boolean isDays = isDays(i, withRemoval);
                        if (!isDays) {
                            max = (i - 1);
                        }
                    }
                }
                if (min == -1) {
                    min = 0;
                }
                if (max == -1) {
                    max = withRemoval.length();
                }
                String temp = "";
                for (int i = 0; i < withRemoval.length(); i++) {
                    if (i >= min && i <= max) {
                        temp = temp + "$";
                    } else {
                        temp = temp + withRemoval.charAt(i);
                    }
                }
                withRemoval = temp;
            }
        }
        return withRemoval.replaceAll("\\$", "");
    }

    private static boolean isDays(int i, String withRemoval) {
        String raw = "";
        for (int p = 0; p < 5; p++) {
            raw = raw + withRemoval.charAt(i + p);
        }
        return raw.equals("{ddd}");
    }

    private static long getNumber(String format, long time) {
        switch (format) {
            case "d" -> {
                return TimerCalculation.getDays(time);
            }
            case "h" -> {
                return TimerCalculation.getHours(time);
            }
            case "m" -> {
                return TimerCalculation.getMinutes(time);
            }
            case "s" -> {
                return TimerCalculation.getSeconds(time);
            }
            case "q" -> {
                return TimerCalculation.getMilliseconds(time);
            }
            default -> {
                return 0;
            }
        }
    }

    public static String insertNumbers(String style, long time, Language language) {
        String d = String.valueOf(TimerCalculation.getDays(time));
        String h = String.valueOf(TimerCalculation.getHours(time));
        String m = String.valueOf(TimerCalculation.getMinutes(time));
        String s = String.valueOf(TimerCalculation.getSeconds(time));
        String q = String.valueOf(TimerCalculation.getMilliseconds(time));

        String dd;
        String hh;
        String mm;
        String ss;
        String qq;
        String ddd;

        if (TimerCalculation.getDays(time) < 10) {
            dd = "0" + d;
        } else {
            dd = d;
        }

        if (TimerCalculation.getHours(time) < 10) {
            hh = "0" + h;
        } else {
            hh = h;
        }

        if (TimerCalculation.getMinutes(time) < 10) {
            mm = "0" + m;
        } else {
            mm = m;
        }

        if (TimerCalculation.getSeconds(time) < 10) {
            ss = "0" + s;
        } else {
            ss = s;
        }

        if (TimerCalculation.getMilliseconds(time) > 99) {
            qq = q;
        } else {
            if (TimerCalculation.getMilliseconds(time) < 10) {
                qq = "00" + q;
            } else {
                qq = "0" + q;
            }
        }

        if (TimerCalculation.getDays(time) == 1) {
            ddd = MessageAPI.getMessage(MessageID.STYLES_DAY, language);
        } else {
            ddd = MessageAPI.getMessage(MessageID.STYLES_DAYS, language);
        }

        String phase1 = style.replaceAll("\\{d}", d);
        String phase2 = phase1.replaceAll("\\{dd}", dd);
        String phase3 = phase2.replaceAll("\\{ddd}", ddd);
        String phase4 = phase3.replaceAll("\\{h}", h);
        String phase5 = phase4.replaceAll("\\{hh}", hh);
        String phase6 = phase5.replaceAll("\\{m}", m);
        String phase7 = phase6.replaceAll("\\{mm}", mm);
        String phase8 = phase7.replaceAll("\\{s}", s);
        String phase9 = phase8.replaceAll("\\{ss}", ss);
        String phase10 = phase9.replaceAll("\\{q}", q);
        String phase11 = phase10.replaceAll("\\{qq}", qq);

        return phase11;
    }

    public static String getStyle(Style styleID) {
        switch (styleID) {
            case STANDARD -> {
                return "{d}{v} {ddd} {hh}{v}:{mm}:{ss}";
            }
            case STANDARD2 -> {
                return "{d}{v} {ddd} {hh}{v}:{mm}:{ss}.{qq}";
            }
            case STANDARD3 -> {
                return "{d}{v}d {h}{v}h {m}{v}m {s}{v}s";
            }
            case STANDARD4 -> {
                return "{d}{v}d {h}{v}h {m}{v}m {s}{v}s {q}{v}ms";
            }
            default -> {
                return "{d}d {h}h {m}m {s}s {q}ms";
            }
        }
    }

}