package net.orcinus.cavesandtrenches.util;

import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.compress.utils.Lists;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BannerRendererUtil {
    public static final List<String> BM_TAPESTRIES = Util.make(Lists.newArrayList(), strings -> {
        for (DyeColor dyeColor : DyeColor.values()) {
            strings.add(dyeColor.getName() + "_tapestry");
        }
    });
    public static final String BM_ID = "biomemakeover";
    public static final CompatUtil compat = new CompatUtil();

    public BannerRendererUtil() {
    }

    public boolean isBMInstalled() {
        return compat.isModInstalled(BM_ID);
    }

    public boolean isTapestryStack(ItemStack stack) {
        boolean flag = false;
        Item item = stack.getItem();
        if (this.isBMInstalled()) {
            for (String name : BM_TAPESTRIES) {
                if (compat.matchesCompatItem(item, BM_ID, "adjudicator_tapestry") || compat.matchesCompatItem(item, BM_ID, name)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public String getName() {
        String name = null;
        for (String names : BM_TAPESTRIES) {
            name = names;
        }
        return name;
    }

}
