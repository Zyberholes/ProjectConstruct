package com.zyberholes.procon.items;

import com.zyberholes.procon.CustomTabs;
import com.zyberholes.procon.Reference;
import com.zyberholes.procon.entities.EntityBolt;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class IronNugget extends Item {

	public IronNugget() 
	{
		setUnlocalizedName(Reference.MODID+"_"+"iron_nugget");
		setTextureName(Reference.ASSETS + ":" + "iron_nugget");
		
		setCreativeTab(CustomTabs.conMatTab);
	}
	
}
