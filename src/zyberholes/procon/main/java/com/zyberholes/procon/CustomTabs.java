package com.zyberholes.procon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CustomTabs {
	
	public static CreativeTabs conBaseTab = new CreativeTabs("constructBase") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Blocks.redstone_lamp);
		}
	};
	
	public static CreativeTabs conPartsTab = new CreativeTabs("constructParts") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return ProConMod.bolt;
		}
	};
	
	public static CreativeTabs conMatTab = new CreativeTabs("constructMaterial") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return ProConMod.ironNugget;
		}
	};
	
}
