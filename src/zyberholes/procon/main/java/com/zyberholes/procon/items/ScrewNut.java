package com.zyberholes.procon.items;

import com.zyberholes.procon.CustomTabs;
import com.zyberholes.procon.Reference;

import net.minecraft.item.Item;

public class ScrewNut extends Item {
	
	public ScrewNut() {
		setUnlocalizedName(Reference.MODID+"_"+"screw_nut");
		setTextureName(Reference.ASSETS + ":" + "screw_nut");
		
		setCreativeTab(CustomTabs.conPartsTab);
	}
	
}
