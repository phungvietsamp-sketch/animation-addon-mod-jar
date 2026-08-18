package com.example.animationmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnimationMod implements ModInitializer {
    public static final String MOD_ID = "animationmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Item ANIMATED_ITEM = new Item(new Item.Settings());

    public static final Block ANIMATED_BLOCK = new Block(AbstractBlock.Settings.create()
            .strength(5.0f, 6.0f)
            .requiresTool());

    public static final BlockEntityType<AnimatedBlockEntity> ANIMATED_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(MOD_ID, "animated_block_entity"),
            BlockEntityType.Builder.create(AnimatedBlockEntity::new, ANIMATED_BLOCK).build(null)
    );

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Animation Mod");

        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "animated_item"), ANIMATED_ITEM);
        Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, "animated_block"), ANIMATED_BLOCK);
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "animated_block"),
                new BlockItem(ANIMATED_BLOCK, new Item.Settings()));

        Registry.register(Registries.ITEM_GROUP, Identifier.of(MOD_ID, "animationmod_group"),
                ItemGroups.register(Identifier.of(MOD_ID, "animationmod_group"),
                        builder -> builder.icon(() -> ANIMATED_ITEM.getDefaultStack())
                                .displayName(net.minecraft.text.Text.translatable("itemGroup.animationmod"))
                                .entries((displayContext, entries) -> {
                                    entries.add(ANIMATED_ITEM);
                                    entries.add(Registry.ITEM.get(Identifier.of(MOD_ID, "animated_block")));
                                })));
    }

    public static class AnimatedBlockEntity extends BlockEntity {
        private float animationProgress = 0f;
        private float prevAnimationProgress = 0f;
        private boolean animating = true;

        public AnimatedBlockEntity(BlockPos pos, BlockState state) {
            super(ANIMATED_BLOCK_ENTITY, pos, state);
        }

        public static void tick(World world, BlockPos pos, BlockState state, AnimatedBlockEntity blockEntity) {
            if (world.isClient()) {
                blockEntity.prevAnimationProgress = blockEntity.animationProgress;

                if (blockEntity.animating) {
                    blockEntity.animationProgress += 0.02f;
                    if (blockEntity.animationProgress >= 1.0f) {
                        blockEntity.animationProgress = 0f;
                    }
                }
            }
        }

        public float getAnimationProgress(float partialTick) {
            return prevAnimationProgress + (animationProgress - prevAnimationProgress) * partialTick;
        }

        public void setAnimating(boolean animating) {
            this.animating = animating;
        }
    }
}