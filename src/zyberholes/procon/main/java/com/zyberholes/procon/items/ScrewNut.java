package com.zyberholes.procon.items;

import com.zyberholes.procon.CustomTabs;
import com.zyberholes.procon.Reference;
import com.zyberholes.procon.entities.EntityThrowablePart;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ScrewNut extends Item {
	
	public ScrewNut() {
		setUnlocalizedName(Reference.MODID+"_"+"screw_nut");
		setTextureName(Reference.ASSETS + ":" + "screw_nut");
		
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
			par2World.spawnEntityInWorld(new EntityThrowablePart(par2World, par3EntityPlayer, itms.getItem()));
		}
		
		return itms;
	}
	
}
