package net.orcinus.cavesandtrenches.util;

import com.google.common.collect.Lists;
import net.minecraft.Util;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

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

}
