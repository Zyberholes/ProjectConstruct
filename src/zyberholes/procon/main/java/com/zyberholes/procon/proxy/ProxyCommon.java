package com.zyberholes.procon.proxy;

import com.zyberholes.procon.Reference;
import com.zyberholes.procon.tileentities.TileEntityAlloyFurnace;
import com.zyberholes.procon.tileentities.TileEntityGenerator;

import cpw.mods.fml.common.registry.GameRegistry;

public class ProxyCommon {
	
	public void registerRenderers() {
		
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityGenerator.class, Reference.MODID+":TileEntityGenerator");
		GameRegistry.registerTileEntity(TileEntityAlloyFurnace.class, Reference.MODID+":TileEntityAlloyFurnace");
	}
}
