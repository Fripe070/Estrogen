package dev.mayaqq.estrogen.client.cosmetics.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public class CosmeticPreview extends AbstractWidget {

    private final Player player;
    private float rotation = Mth.PI / 4;

    public CosmeticPreview(int x, int y, int width, int height) {
        super(x, y, width, height, CommonComponents.EMPTY);

        this.player = Minecraft.getInstance().player;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.player != null) {
            Quaternionf quaternion = new Quaternionf().rotateZ(Mth.PI).rotateY(this.rotation);
            float yHeadRot = this.player.yBodyRot;
            float yRot = this.player.getYRot();
            float xRot = this.player.getXRot();
            float yHeadRotO = this.player.yHeadRotO;
            float yBodyRot = this.player.yHeadRot;
            this.player.yBodyRot = 180.0F;
            this.player.setYRot(180.0F);
            this.player.setXRot(0f);
            this.player.yHeadRot = this.player.getYRot();
            this.player.yHeadRotO = this.player.getYRot();
            InventoryScreen.renderEntityInInventory(
                    graphics,
                    (int) (getX() + getWidth() / 2f), getY() + getHeight() - 20,
                    50, quaternion, null, this.player
            );
            this.player.yBodyRot = yHeadRot;
            this.player.setYRot(yRot);
            this.player.setXRot(xRot);
            this.player.yHeadRot = yHeadRotO;
            this.player.yHeadRotO = yBodyRot;
        }
    }

    @Override
    public boolean mouseDragged(double d, double e, int i, double f, double g) {
        this.rotation += (float) f * 0.15F;
        return true;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {

    }
}