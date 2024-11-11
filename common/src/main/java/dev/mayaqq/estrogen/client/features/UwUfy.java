package dev.mayaqq.estrogen.client.features;

import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.minecraft.client.Minecraft;

import java.util.regex.Pattern;

public class UwUfy {

    private static final String[] PHRASES = {
            "UwU",
            "owo",
            "OwO",
            "uwu",
            ">w<",
            "^w^",
            ":3",
            "^-^",
            "^_^",
            "^w^",
            ":3"
    };

    // should the player be uwufied
    private static boolean isEnabled = false;

    public static void tick() {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;
        if (client.player.tickCount % 20 == 0) {
            isEnabled = client.player.getInventory().contains(EstrogenTags.Items.UWUFYING);
            client.updateTitle();
        }
    }

    public static void disconnect() {
        isEnabled = false;
    }

    public static String uwufyString(String input) {
        int stringLength = input.length();
        // Replace 'r' and 'l' with 'w', and 'R' and 'L' with 'W'
        // Replace 'ove' with 'uv' and 'OVE' with 'UV'
        // Replace 'o' with 'owo' and 'O' with 'OwO'
        // Replace repeated exclamation marks and question marks
        input = input
                .replaceAll("[rl]", "w").replaceAll("[RL]", "W")
                .replaceAll("ove", "uv").replaceAll("OVE", "UV")
                .replaceAll("o", "owo").replaceAll("O", "OwO")
                .replaceAll("!", "!!!").replaceAll("\\?", "???");

        // Convert to uppercase
        if (stringLength % 3 == 0) {
            input = input.toUpperCase();
        }

        input = Pattern.compile("%(\\p{L})").matcher(input).replaceAll(m -> "%" + m.group(1).toLowerCase());
        input = Pattern.compile("\\$(\\p{L})").matcher(input).replaceAll(m -> "\\$" + m.group(1).toLowerCase());

        if (stringLength % 2 == 0) {
            // Add more letters to the end of words (Not numbers!)
            input = input.replaceAll("([\\p{L}])(\\b)", "$1$1$1$1$2");
        } else {
            // 50% chance to duplicate the first letter and add '-'
            input = input.replaceAll("\\b([\\p{L}])(\\p{L}*)\\b", "$1-$1$2");
        }

        return input + " " + PHRASES[stringLength % PHRASES.length];
    }

    public static boolean isEnabled() {
        return isEnabled;
    }
}
