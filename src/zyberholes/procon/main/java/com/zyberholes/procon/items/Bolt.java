package com.zyberholes.procon.items;

import com.zyberholes.procon.CustomTabs;
import com.zyberholes.procon.Reference;
import com.zyberholes.procon.entities.EntityBolt;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Bolt extends Item {

	public Bolt() 
	{
		setUnlocalizedName(Reference.MODID+"_"+"bolt");
		setTextureName(Reference.ASSETS + ":" + "bolt");
		
		setCreativeTab(CustomTabs.conPartsTab);
	}
	
	public ItemStack onItemRightClick(ItemStack itms, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par3EntityPlayer.capabilities.isCreativeMode)
		{
			--itms.stackSize;
		}
		
		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if (!par2World.isRemote)
		{
			par2World.spawnEntityInWorld(new EntityBolt(par2World, par3EntityPlayer, itms.getItem()));
		}
		
		return itms;
	}
	
}
