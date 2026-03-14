package net.crsimple.usecurity.client;

import net.crsimple.usecurity.ModMain;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import tschipp.carryon.Constants;

import java.util.concurrent.CompletableFuture;

public class ModMainDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(TagGenerator::new);
    }

    public static class TagGenerator extends FabricTagProvider.BlockTagProvider {

        public TagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wl) {
            TagKey<Block> tag = TagKey.of(RegistryKeys.BLOCK,new Identifier(Constants.MOD_ID,"black_list1"));
            TagKey<Block> tag2 = TagKey.of(RegistryKeys.BLOCK,new Identifier(ModMain.ID,"reinforced"));
            getOrCreateTagBuilder(tag).addTag(tag2);
        }
    }
}
