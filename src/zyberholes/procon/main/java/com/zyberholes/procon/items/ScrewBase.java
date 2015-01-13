package com.zyberholes.procon.items;

import com.zyberholes.procon.CustomTabs;
import com.zyberholes.procon.Reference;

import net.minecraft.item.Item;

public class ScrewBase extends Item {
	
	public ScrewBase() {
		setUnlocalizedName(Reference.MODID+"_"+"screw_base");
		setTextureName(Reference.ASSETS + ":" + "screw_base");
		
		setCreativeTab(CustomTabs.conPartsTab);
	}
	
}
