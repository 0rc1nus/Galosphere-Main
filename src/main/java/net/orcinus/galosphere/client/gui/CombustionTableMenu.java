package net.orcinus.galosphere.client.gui;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.init.GMenuTypes;
import net.orcinus.galosphere.util.CompatUtil;

public class CombustionTableMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    public final Container container = new SimpleContainer(4) {
        public void setChanged() {
            CombustionTableMenu.this.slotsChanged(this);
            super.setChanged();
        }
    };
    private final ResultContainer resultContainer = new ResultContainer() {
        public void setChanged() {
            CombustionTableMenu.this.slotsChanged(this);
            super.setChanged();
        }
    };

    public CombustionTableMenu(int id, Inventory inventory, final ContainerLevelAccess access) {
        super(GMenuTypes.COMBUSTION_TABLE, id);
        this.access = access;
        this.addSlot(new Slot(this.container, 0, 44, 29) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(GItems.SILVER_BOMB);
            }
        });
        this.addSlot(new CombustionSlot(this.container, 1, 44, 50));
        this.addSlot(new CombustionSlot(this.container, 2, 80 , 50));
        this.addSlot(new CombustionSlot(this.container, 3, 116 , 50));
        this.addSlot(new Slot(this.resultContainer, 4, 79, 21) {

            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            public void onTake(Player player, ItemStack stack) {
                CombustionTableMenu.this.slots.get(0).remove(1);
                CombustionTableMenu.this.slots.get(1).remove(1);
                CombustionTableMenu.this.slots.get(2).remove(1);
                CombustionTableMenu.this.slots.get(3).remove(1);
                super.onTake(player, stack);
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }

    }

    public CombustionTableMenu(int id, Inventory inventory) {
        this(id, inventory, ContainerLevelAccess.NULL);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, GBlocks.COMBUSTION_TABLE);
    }

    @Override
    public void slotsChanged(Container container) {
        ItemStack bombStack = this.container.getItem(0);
        ItemStack exploStat1 = this.container.getItem(1);
        ItemStack exploStat2 = this.container.getItem(2);
        ItemStack exploStat3 = this.container.getItem(3);
        CompoundTag tag = bombStack.getOrCreateTag();
        int bouncy = tag.getInt("Bouncy");
        int explosion = tag.getInt("Explosion");
        int duration = tag.getInt("Duration");
        boolean initFlag = true;
        if (bombStack.is(GItems.SILVER_BOMB)) {
            if (!exploStat1.isEmpty() || !exploStat2.isEmpty() || !exploStat3.isEmpty()) {
                CompoundTag currentTag = bombStack.getTag();
                int bouncyCount = 0;
                int durationCount = 0;
                int explosionCount = 0;
                if (currentTag != null) {
                    for (int i = 1; i < 4; i++) {
                        ItemStack item = this.container.getItem(i);
                        if (item.is(Items.SLIME_BALL)) bouncyCount++;
                        if (item.is(Items.STRING)) durationCount++;
                        if (item.is(Items.GUNPOWDER)) explosionCount++;
                        if (bouncy + bouncyCount <= 3 && duration + durationCount <= 3 && explosion + explosionCount <= 3) {
                            initFlag = true;
                        } else {
                            initFlag = false;
                            this.resultContainer.removeItemNoUpdate(4);
                        }
                    }
                }
                ItemStack resultCopy = bombStack.copy();
                resultCopy.setCount(1);
                resultCopy.getOrCreateTag().putInt("Explosion", explosion + explosionCount);
                resultCopy.getOrCreateTag().putInt("Bouncy", bouncy + bouncyCount);
                resultCopy.getOrCreateTag().putInt("Duration", duration + durationCount);
                if (initFlag) {
                    this.resultContainer.setItem(4, resultCopy);
                }
            } else {
                this.resultContainer.removeItemNoUpdate(4);
            }
        } else {
            this.resultContainer.removeItemNoUpdate(4);
        }
    }

    public static Item getLeadIngot() {
        CompatUtil compatUtil = new CompatUtil();
        String modid = "oreganized";
        Item item = null;
        if (compatUtil.isModInstalled(modid)) item = compatUtil.getCompatItem(modid, "lead_ingot");
        return item;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.resultContainer.removeItemNoUpdate(4);
        this.access.execute((world, pos) -> {
            this.clearContainer(player, this.container);
        });
    }

    @Override
    public ItemStack quickMoveStack(Player player, int id) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(id);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();
            ItemStack itemstack2 = this.container.getItem(0);
            ItemStack itemstack3 = this.container.getItem(1);
            ItemStack itemstack4 = this.container.getItem(2);
            ItemStack itemstack5 = this.container.getItem(3);
            if (id == 4) {
                slotStack.getItem().onCraftedBy(slotStack, player.level, player);
                if (!this.moveItemStackTo(slotStack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(slotStack, itemstack);
            } else if (id != 0 && id != 1 && id != 2 && id != 3) {
                if (!itemstack2.isEmpty() && !itemstack3.isEmpty() && !itemstack4.isEmpty() && !itemstack5.isEmpty()) {
                    if (id >= 4 && id < 30) {
                        if (!this.moveItemStackTo(slotStack, 30, 39, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (id >= 30 && id < 39 && !this.moveItemStackTo(slotStack, 4, 30, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(slotStack, 0, 4, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 5, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (slotStack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
            this.broadcastChanges();

        }

        return itemstack;
    }

    public static class CombustionSlot extends Slot {

        public CombustionSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.is(Items.STRING) || stack.is(Items.GUNPOWDER) || stack.is(Items.SLIME_BALL) || stack.is(getLeadIngot());
        }

    }

}