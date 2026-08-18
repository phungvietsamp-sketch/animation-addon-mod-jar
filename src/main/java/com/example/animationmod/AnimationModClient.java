package com.example.animationmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EntityRendererFactories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnimationModClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("animationmod-client");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Animation Mod Client");

        BlockEntityRendererRegistry.register(AnimationMod.ANIMATED_BLOCK_ENTITY, AnimatedBlockEntityRenderer::new);
    }
}