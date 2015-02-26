package com.zyberholes.procon.tileentities;

import com.zyberholes.procon.ProConMod;
import com.zyberholes.procon.blocks.MachineGenerator;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGenerator extends TileEntity implements ISidedInventory {
	
	private static final int[] slotsTop = new int[]{ 0 };
	private static final int[] slotsBottom = new int[]{ 2,1 };
	private static final int[] slotsSides = new int[]{ 1 };
	
	private ItemStack[] generatorItemStacks = new ItemStack[3];
	
	private int stackLimit = 64;
	
	public int generatorBurnTime; //How long the generator burns for
	public int currentBurnTime; //Current burn time for the generator
	
	public int generatorCookTime; //How long the Item has been in the generator
	
	private String generatorName; //Name inside GUI
	
	public void setGeneratorName(String name) {
		this.generatorName = name;
	}
	
	@Override
	public int getSizeInventory() {
		return this.generatorItemStacks.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.generatorItemStacks[slot];
	}
	
	//Taking items out of slots:
	@Override
	public ItemStack decrStackSize(int slot, int takenItems) {
		if (this.generatorItemStacks[slot] != null) {
			ItemStack itemStack;
			
			if (this.generatorItemStacks[slot].stackSize <= takenItems) { //What happens when the amount in the stack (or less) is being taken?
				itemStack = this.generatorItemStacks[slot];
				this.generatorItemStacks[slot] = null;
				return itemStack;
			}else {
				itemStack = this.generatorItemStacks[slot].splitStack(takenItems);
				
				if (this.generatorItemStacks[slot].stackSize == 0) {
					this.generatorItemStacks[slot] = null;
				}
				
				return itemStack;
			}
		}else {
			return null;
		}
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.generatorItemStacks[slot] != null) {
			ItemStack itemStack = this.generatorItemStacks[slot];
			return itemStack;
		}else {
			return null;
		}
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		this.generatorItemStacks[slot] = itemStack;
		
		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.generatorName : "Unknown Generator";
	}
	@Override
	public boolean hasCustomInventoryName() {
		return (this.generatorName != null && this.generatorName.length() > 0) ? true : false;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return this.stackLimit;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
			return false;
		}else {
			return (player.getDistanceSq((double) this.xCoord+0.5, (double) this.yCoord+0.5, (double) this.zCoord+0.5) <= 64.0);
		}
	}
	
	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
	}
	
	//Check if you can put item in slot
	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemStack) {
		return par1 == 2 ? false : (par1 == 1 ? isItemFuel(itemStack) : true);
	}
	
	//For automated machines
	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		return par1 == 0 ? slotsBottom : (par1 == 1 ? slotsTop : slotsSides);
	}
	@Override
	public boolean canInsertItem(int par1, ItemStack itemStack, int par3) {
		return this.isItemValidForSlot(par1, itemStack);
	}
	@Override
	public boolean canExtractItem(int par1, ItemStack itemStack, int par3) {
		return (par3 != 0 || par1 != 1 || itemStack.getItem() == Items.bucket); //Last part is for lava buckets
	}
	
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		this.generatorItemStacks = new ItemStack[this.getSizeInventory()];
		
		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tagCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tagCompound1.getByte("Slot");
			
			if (byte0 >= 0 && byte0 < this.generatorItemStacks.length) {
				this.generatorItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tagCompound1);
			}
		}
		
		this.generatorBurnTime = tagCompound.getShort("BurnTime");
		this.generatorCookTime = tagCompound.getShort("CookTime");
		this.currentBurnTime = getItemBurnTime(this.generatorItemStacks[1]);
		
		if (tagCompound.hasKey("CustomName", 8)) {
			this.generatorName = tagCompound.getString("CustomName");
		}
	}
	
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setShort("BurnTime", (short) this.generatorBurnTime);
		tagCompound.setShort("CookTime", (short) this.generatorCookTime);
		NBTTagList tagList = new NBTTagList();
		
		for (int i = 0; i < this.generatorItemStacks.length; ++i) {
			if (this.generatorItemStacks[i] != null) {
				NBTTagCompound tagCompound1 = new NBTTagCompound();
				tagCompound.setByte("Slot",(byte) i);
				this.generatorItemStacks[i].writeToNBT(tagCompound1);
				tagList.appendTag(tagCompound1);
			}
		}
		
		tagCompound.setTag("Items", tagList);
		
		if (this.hasCustomInventoryName()) {
			tagCompound.setString("CustomName", this.generatorName);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.generatorCookTime * par1 / 200;
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentBurnTime == 0) {
			this.currentBurnTime = 200;
		}
		
		return this.generatorBurnTime * par1 / this.currentBurnTime;
	}
	
	public static boolean isItemFuel(ItemStack itemStack) {
		return getItemBurnTime(itemStack) > 0;
	}
	
	//Get fuel effectivity:
	public static int getItemBurnTime(ItemStack itemStack) {
		if (itemStack == null) {
			return 0;
		}else {
			Item item = itemStack.getItem();
			
			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
				Block block = Block.getBlockFromItem(item);
				
				if (block == Blocks.log) return 200;
				if (block.getMaterial() == Material.wood) return 200;
			}
			
			if (item == Items.coal) return 1600;
			return GameRegistry.getFuelValue(itemStack); //Get any fuel in worst case!
		}
	}
	
	public boolean isBurning() {
		return this.generatorBurnTime > 0; //Will return true if has burn time
	}
	
	//Check if we can burn item:
	private boolean canSmelt() {
		if (this.generatorItemStacks[0] == null) {
			return false;
		}else {
			ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.generatorItemStacks[0]);
			if (itemStack == null) {
				return false;
			}
			if (this.generatorItemStacks[2] == null) {
				return true;
			}
			
			if (this.generatorItemStacks[2].isItemEqual(itemStack)) {
				int result = this.generatorItemStacks[2].stackSize + itemStack.stackSize;
				return (result <= getInventoryStackLimit() && result <= this.generatorItemStacks[2].getMaxStackSize());
			}
			
			return false;
		}
	}
	
	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.generatorItemStacks[0]);
			
			if (this.generatorItemStacks[2] == null) {
				this.generatorItemStacks[2] = itemStack.copy();
			}else if (this.generatorItemStacks[2].getItem() == itemStack.getItem()) {
				this.generatorItemStacks[2].stackSize += itemStack.stackSize;
			}
			
			--this.generatorItemStacks[0].stackSize;
			
			if (this.generatorItemStacks[0].stackSize == 0) {
				this.generatorItemStacks[0] = null;
			}
		}
	}
	
	//Updates Block state, Burn Time, Progress and Cook Time
	public void updateEntity() {
		boolean flag = this.generatorBurnTime > 0;
		boolean flag1 = false;
		
		if (this.generatorBurnTime > 0) {
			--this.generatorBurnTime;
		}
		
		if (!this.worldObj.isRemote) {
			if (this.generatorBurnTime == 0 && this.canSmelt()) {
				this.currentBurnTime = this.generatorBurnTime = getItemBurnTime(this.generatorItemStacks[1]);
				
				if (this.generatorBurnTime > 0) {
					flag1 = true;
					
					if (this.generatorItemStacks[1] != null) {
						--this.generatorItemStacks[1].stackSize;
						
						if (this.generatorItemStacks[1].stackSize == 0) { //Check if run out of items
							this.generatorItemStacks[1] = this.generatorItemStacks[1].getItem().getContainerItem(this.generatorItemStacks[1]);
						}
					}
				}
			}
			
			if (this.isBurning() && this.canSmelt()) {
				++this.generatorCookTime;
				
				if (this.generatorCookTime == 200) {
					this.generatorCookTime = 0;
					this.smeltItem();
					flag1 = true;
				}
			}else {
				this.generatorCookTime = 0;
			}
		}
		
		if (flag != this.generatorBurnTime > 0) {
			flag1 = true;
			MachineGenerator.updateBlockState(this.generatorBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
		
		if (flag1) {
			this.markDirty();
		}
	}
	
	
}
