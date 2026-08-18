package com.example.animationmod;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class AnimatedBlockEntityRenderer implements BlockEntityRenderer<AnimationMod.AnimatedBlockEntity> {
    public AnimatedBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(AnimationMod.AnimatedBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        float progress = entity.getAnimationProgress(tickDelta);

        matrices.push();
        matrices.translate(0.5, 0.5, 0.5);

        float rotation = progress * 360f;
        matrices.multiply(Axis.YP.rotationDegrees(rotation));

        float scale = 0.5f + (float)Math.sin(progress * Math.PI * 2) * 0.1f;
        matrices.scale(scale, scale, scale);

        matrices.translate(-0.5, -0.5, -0.5);

        // Render a simple item as the animated block
        ItemStack stack = new ItemStack(Items.DIAMOND_BLOCK);
        net.minecraft.client.render.item.ItemRenderer itemRenderer = net.minecraft.client.MinecraftClient.getInstance().getItemRenderer();
        itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);

        matrices.pop();
    }
}