package com.zyberholes.procon;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.zyberholes.procon.proxy.ProxyCommon;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ProConMod.MODID, version = ProConMod.VERSION)
public class ProConMod
{
	public static final String MODID = "procon";
	public static final String VERSION = "0.1";
	
	@Instance(value = MODID)
	public static ProConMod instance;
	
	@SidedProxy(clientSide = "com.zyberholes.procon.proxy.ProxyClient", serverSide = "com.zyberholes.procon.proxy.ProxyCommon")
	public static ProxyCommon proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.addRecipe(new ItemStack(Blocks.wool, 4), "DD", "DD", 'D', Blocks.dirt);
		GameRegistry.addRecipe(new ItemStack(Items.flint_and_steel, 1), "x ", " x", 'x', Blocks.dirt);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
}
