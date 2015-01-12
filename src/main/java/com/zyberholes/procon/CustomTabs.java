package com.zyberholes.procon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CustomTabs {
	
	public static CreativeTabs conTab = new CreativeTabs("construct") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return ProConMod.bolt;
		}
	};
	
}
