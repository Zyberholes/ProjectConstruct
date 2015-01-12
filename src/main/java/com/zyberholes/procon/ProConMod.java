package com.zyberholes.procon;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.zyberholes.procon.entities.EntityBolt;
import com.zyberholes.procon.items.Bolt;
import com.zyberholes.procon.items.IronNugget;
import com.zyberholes.procon.items.ScrewBase;
import com.zyberholes.procon.items.ScrewNut;
import com.zyberholes.procon.proxy.ProxyCommon;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MODID, name = "Project Construct", version = Reference.VERSION)
public class ProConMod
{
	@Instance(value = Reference.MODID)
	public static ProConMod instance;
	
	//define mod content
	public static Item bolt;
	public static Item screwBase;
	public static Item screwNut;
	public static Item ironNugget;
	
	@SidedProxy(clientSide = "com.zyberholes.procon.proxy.ProxyClient", serverSide = "com.zyberholes.procon.proxy.ProxyCommon")
	public static ProxyCommon proxy;
	
	//where most creation takes place!
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		bolt = new Bolt();
		screwBase = new ScrewBase();
		screwNut = new ScrewNut();
		ironNugget = new IronNugget();
		
		//register items:
		GameRegistry.registerItem(bolt, "bolt");
		GameRegistry.registerItem(screwBase, "screwBase");
		GameRegistry.registerItem(screwNut, "screwNut");
		GameRegistry.registerItem(ironNugget, "ironNugget");
		
		//shaped crafting:
		GameRegistry.addRecipe(new ItemStack(bolt, 2), new Object[] {"nnn", " b ", " b ", 'n', ironNugget, 'b', screwBase});
		GameRegistry.addRecipe(new ItemStack(screwBase, 2), new Object[] {"n ", " n", "n ", 'n', ironNugget});
		GameRegistry.addRecipe(new ItemStack(screwBase, 2), new Object[] {" n", "n ", " n", 'n', ironNugget});
		GameRegistry.addRecipe(new ItemStack(screwNut, 2), new Object[] {" nn", "n n", "nn ", 'n', ironNugget});
		GameRegistry.addRecipe(new ItemStack(screwNut, 2), new Object[] {"nn ", "n n", " nn", 'n', ironNugget});
		GameRegistry.addRecipe(new ItemStack(Items.iron_ingot, 1), new Object[] {"iii", "iii", "iii", 'i', ironNugget});
		
		//shapeless crafting:
		GameRegistry.addShapelessRecipe(new ItemStack(ironNugget, 9), Items.iron_ingot);
		
		//smelting:
		GameRegistry.addSmelting(bolt, new ItemStack(ironNugget, 3), 0F);
		GameRegistry.addSmelting(screwBase, new ItemStack(ironNugget, 1), 0F);
		GameRegistry.addSmelting(screwNut, new ItemStack(ironNugget, 3), 0F);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		EntityRegistry.registerModEntity(EntityBolt.class, "entityBolt", 4, this, 80, 3, true);
		proxy.registerRenderers();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
}
