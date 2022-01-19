package net.orcinus.cavesandtrenches.client.gui;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.orcinus.cavesandtrenches.init.CTBlocks;
import net.orcinus.cavesandtrenches.init.CTItems;
import net.orcinus.cavesandtrenches.init.CTMenuTypes;

public class CombustionTableMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    public final Container container = new SimpleContainer(3) {
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
        super(CTMenuTypes.COMBUSTION_TABLE.get(), id);
        this.access = access;
        this.addSlot(new Slot(this.container, 0, 18, 17) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(CTItems.SILVER_BOMB.get());
            }
        });
        this.addSlot(new Slot(this.container, 1, 7, 54) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.STRING) || stack.is(Items.GUNPOWDER) || stack.is(Items.SLIME_BALL);
            }
        });
        this.addSlot(new Slot(this.container, 2, 28, 54) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.STRING) || stack.is(Items.GUNPOWDER) || stack.is(Items.SLIME_BALL);
            }
        });
        this.addSlot(new Slot(this.resultContainer, 3, 143, 37) {

            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            public void onTake(Player player, ItemStack stack) {
                CombustionTableMenu.this.slots.get(0).remove(1);
                CombustionTableMenu.this.slots.get(1).remove(1);
                CombustionTableMenu.this.slots.get(2).remove(1);
                stack.getItem().onCraftedBy(stack, player.level, player);
                super.onTake(player, stack);
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }

    }

    public CombustionTableMenu(int id, Inventory inventory) {
        this(id, inventory, ContainerLevelAccess.NULL);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, CTBlocks.COMBUSTION_TABLE.get());
    }

    @Override
    public void slotsChanged(Container container) {
        ItemStack bombStack = this.container.getItem(0);
        ItemStack exploStat1 = this.container.getItem(1);
        ItemStack exploStat2 = this.container.getItem(2);
        CompoundTag tag = bombStack.getOrCreateTag();
        int bouncy = tag.getInt("Bouncy");
        int explosion = tag.getInt("Explosion");
        int duration = tag.getInt("Duration");
        boolean initFlag = true;
        if (bombStack.is(CTItems.SILVER_BOMB.get())) {
            if (!exploStat1.isEmpty() || !exploStat2.isEmpty()) {
                CompoundTag currentTag = bombStack.getTag();
                if (exploStat1.is(Items.SLIME_BALL)) {
                    if (currentTag != null) {
                        if (currentTag.getInt("Bouncy") == 3) {
                            initFlag = false;
                            this.resultContainer.removeItemNoUpdate(3);
                        }
                    }
                    bouncy++;
                }
                if (exploStat1.is(Items.STRING)) {
                    if (currentTag != null) {
                        if (currentTag.getInt("Duration") == 3) {
                            initFlag = false;
                            this.resultContainer.removeItemNoUpdate(3);
                        }
                    }
                    duration++;
                }
                if (exploStat1.is(Items.GUNPOWDER)) {
                    if (currentTag != null) {
                        if (currentTag.getInt("Explosion") == 3) {
                            initFlag = false;
                            this.resultContainer.removeItemNoUpdate(3);
                        }
                    }
                    explosion++;
                }
                if (exploStat2.is(Items.SLIME_BALL)) {
                    if (currentTag != null) {
                        if (currentTag.getInt("Bouncy") == 3) {
                            initFlag = false;
                            this.resultContainer.removeItemNoUpdate(3);
                        }
                    }
                    bouncy++;
                }
                if (exploStat2.is(Items.STRING)) {
                    if (currentTag != null) {
                        if (currentTag.getInt("Duration") == 3) {
                            initFlag = false;
                            this.resultContainer.removeItemNoUpdate(3);
                        }
                    }
                    duration++;
                }
                if (exploStat2.is(Items.GUNPOWDER)) {
                    if (currentTag != null) {
                        if (currentTag.getInt("Explosion") == 3) {
                            initFlag = false;
                            this.resultContainer.removeItemNoUpdate(3);
                        }
                    }
                    explosion++;
                }
                ItemStack resultCopy = bombStack.copy();
                resultCopy.getOrCreateTag().putInt("Explosion", explosion);
                resultCopy.getOrCreateTag().putInt("Bouncy", bouncy);
                resultCopy.getOrCreateTag().putInt("Duration", duration);
                resultCopy.setCount(1);
                if (initFlag) {
                    this.resultContainer.setItem(3, resultCopy);
                }
            } else {
                this.resultContainer.removeItemNoUpdate(3);
            }
        } else {
            this.resultContainer.removeItemNoUpdate(3);
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((world, pos) -> {
            this.clearContainer(player, this.container);
        });
    }

    @Override
    public ItemStack quickMoveStack(Player player, int id) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(id);
        if (slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            if (this.container.getItem(0).isEmpty() && slotItem.is(CTItems.SILVER_BOMB.get())) {
                if (this.moveItemStackTo(slotItem, 0, 1, true)) return ItemStack.EMPTY;
            }
            else if (this.container.getItem(1).isEmpty() && (slotItem.is(Items.GUNPOWDER) || slotItem.is(Items.STRING) || slotItem.is(Items.SLIME_BALL))) {
                if (this.moveItemStackTo(slotItem, 1, 2, true)) return ItemStack.EMPTY;
            }
            else if (this.container.getItem(2).isEmpty() && (slotItem.is(Items.GUNPOWDER) || slotItem.is(Items.STRING) || slotItem.is(Items.SLIME_BALL))) {
                if (this.moveItemStackTo(slotItem, 2, 3, true)) return ItemStack.EMPTY;
            }
            else if (id == 3 && !this.container.getItem(3).isEmpty()) {
                if (!this.moveItemStackTo(slotItem, 3, 40, true)) return ItemStack.EMPTY;
            }
            else if (id == 0 && !this.container.getItem(0).isEmpty()) {
                if (!this.moveItemStackTo(slotItem, 3, 40, true)) return ItemStack.EMPTY;
            }
            else if (id == 1 && !this.container.getItem(1).isEmpty()) {
                if (!this.moveItemStackTo(slotItem, 3, 40, true)) return ItemStack.EMPTY;
            }
            else if (id == 2 && !this.container.getItem(2).isEmpty()) {
                if (!this.moveItemStackTo(slotItem, 3, 40, true)) return ItemStack.EMPTY;
            }
        }
//        ItemStack itemstack = ItemStack.EMPTY;
//        Slot slot = this.slots.get(id);
//        if (slot.hasItem()) {
//            ItemStack itemstack1 = slot.getItem();
//            itemstack = itemstack1.copy();
//            ItemStack itemstack2 = this.container.getItem(0);
//            ItemStack itemstack3 = this.container.getItem(1);
//            ItemStack itemstack4 = this.container.getItem(2);
//            if (id == 3) {
//                if (!this.moveItemStackTo(itemstack1, 3, 40, true)) {
//                    return ItemStack.EMPTY;
//                }
//
//                if (!this.moveItemStackTo(itemstack1, 3, 40, true)) return ItemStack.EMPTY;
//
//                slot.onQuickCraft(itemstack1, itemstack);
//            }
//            else if (id != 0 && id != 1 && id != 2) {
//                if (!itemstack2.isEmpty() && !itemstack3.isEmpty() && !itemstack4.isEmpty()) {
//                    if (id >= 3 && id < 30) {
//                        if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
//                            return ItemStack.EMPTY;
//                        }
//                    } else if (id >= 30 && id < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
//                        return ItemStack.EMPTY;
//                    }
//                }
//                else if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {
//                    return ItemStack.EMPTY;
//                }
//            }
//            else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
//                return ItemStack.EMPTY;
//            }
//
//            if (itemstack1.isEmpty()) {
//                slot.set(ItemStack.EMPTY);
//            }
//            else {
//                slot.setChanged();
//            }
//            if (itemstack1.getCount() == itemstack.getCount()) {
//                return ItemStack.EMPTY;
//            }
//
//            slot.onTake(player, itemstack1);
//            this.broadcastChanges();
//        }
//
//        return itemstack;
        return itemStack;
    }
}
