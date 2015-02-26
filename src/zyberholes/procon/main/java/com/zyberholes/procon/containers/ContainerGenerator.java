package com.zyberholes.procon.containers;

import com.zyberholes.procon.tileentities.TileEntityGenerator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ContainerGenerator extends Container {
	
	private TileEntityGenerator tileEntityGenerator;
	private int lastCookTime, lastBurnTime, lastItemBurnTime;
	
	//Creating GUI layout
	public ContainerGenerator(InventoryPlayer player, TileEntityGenerator tileEntity) {
		this.tileEntityGenerator = tileEntity;
		this.addSlotToContainer(new Slot(tileEntityGenerator, 0, 56, 17));
		this.addSlotToContainer(new Slot(tileEntityGenerator, 1, 56, 53));
		this.addSlotToContainer(new SlotFurnace(player.player, tileEntityGenerator, 2, 116, 35));
		int i;
		
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
	}
	
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.tileEntityGenerator.generatorCookTime);
		craft.sendProgressBarUpdate(this, 1, this.tileEntityGenerator.generatorBurnTime);
		craft.sendProgressBarUpdate(this, 2, this.tileEntityGenerator.currentBurnTime);
	}
	
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting craft = (ICrafting) this.crafters.get(i);
			
			if (this.lastCookTime != this.tileEntityGenerator.generatorCookTime) {
				craft.sendProgressBarUpdate(this, 0, this.tileEntityGenerator.generatorCookTime);
			}
			if (this.lastBurnTime != this.tileEntityGenerator.generatorBurnTime) {
				craft.sendProgressBarUpdate(this, 1, this.tileEntityGenerator.generatorBurnTime);
			}
			if (this.lastItemBurnTime != this.tileEntityGenerator.currentBurnTime) {
				craft.sendProgressBarUpdate(this, 2, this.tileEntityGenerator.currentBurnTime);
			}
		}
		
		this.lastBurnTime = this.tileEntityGenerator.generatorBurnTime;
		this.lastCookTime = this.tileEntityGenerator.generatorCookTime;
		this.lastItemBurnTime = this.tileEntityGenerator.currentBurnTime;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.tileEntityGenerator.generatorCookTime = par2;
		}
		if (par1 == 1) {
			this.tileEntityGenerator.generatorBurnTime = par2;
		}
		if (par1 == 2) {
			this.tileEntityGenerator.currentBurnTime = par2;
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileEntityGenerator.isUseableByPlayer(player);
	}
	
	public ItemStack transferStackInSlot (EntityPlayer player, int par2) {
		ItemStack itemStack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();
			
			if (par2 == 2) {
				if (!this.mergeItemStack(itemStack1, 3, 39, true)) {
					return null;
				}
				slot.onSlotChange(itemStack1, itemStack);
			}else if (par2 != 1 && par2 != 0) {
				if (FurnaceRecipes.smelting().getSmeltingResult(itemStack1) != null) {
					if (!this.mergeItemStack(itemStack1, 0, 1, false)) {
						return null;
					}
				}else if (TileEntityGenerator.isItemFuel(itemStack1)) {
					if (!this.mergeItemStack(itemStack1, 1, 2, false)) {
						return null;
					}
				}else if (par2 >= 3 && par2 < 30) {
					if (!this.mergeItemStack(itemStack1, 30, 39, false)) {
						return null;
					}
				}else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemStack1, 3, 30, false)) {
					return null;
				}
			}else if (!this.mergeItemStack(itemStack1, 3, 39, false)) {
				return null;
			}
			
			if (itemStack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			}else {
				slot.onSlotChanged();
			}
			
			if (itemStack1.stackSize == itemStack.stackSize) {
				return null;
			}
			
			slot.onPickupFromSlot(player, itemStack1);
		}
		
		return itemStack;
	}
	
}
