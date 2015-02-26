package com.zyberholes.procon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.zyberholes.procon.blocks.MachineAlloyFurnace;
import com.zyberholes.procon.blocks.MachineGenerator;
import com.zyberholes.procon.blocks.OreMineral;
import com.zyberholes.procon.entities.EntityBolt;
import com.zyberholes.procon.generation.CustomOreGeneration;
import com.zyberholes.procon.gui.CustomGuiHandler;
import com.zyberholes.procon.gui.GuiGenerator;
import com.zyberholes.procon.items.AluminiumIngot;
import com.zyberholes.procon.items.Bolt;
import com.zyberholes.procon.items.CopperIngot;
import com.zyberholes.procon.items.IronNugget;
import com.zyberholes.procon.items.ScrewBase;
import com.zyberholes.procon.items.ScrewNut;
import com.zyberholes.procon.items.SilverIngot;
import com.zyberholes.procon.items.TinIngot;
import com.zyberholes.procon.items.TungstenIngot;
import com.zyberholes.procon.proxy.ProxyCommon;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MODID, name = "Project Construct", version = Reference.VERSION)
public class ProConMod
{
	//Create mod instance
	@Instance(value = Reference.MODID)
	public static ProConMod instance;
	
	//Define contents
	public static Item itemBolt;
	public static Item itemScrewBase;
	public static Item itemScrewNut;
	public static Item itemIronNugget;
	
	public static Item itemCopperIngot;
	public static Item itemTinIngot;
	public static Item itemSilverIngot;
	public static Item itemTungstenIngot;
	public static Item itemAluminiumIngot;
	
	public static Block oreChalcopyrite;
	public static Block oreCassiterite;
	public static Block oreArgentite;
	public static Block oreWolframite;
	public static Block oreBauxite;
	
	//Machines
	public static Block machineGeneratorIdle;
	public static Block machineGeneratorActive;
	public static Block machineAlloyFurnaceIdle;
	public static Block machineAlloyFurnaceActive;
	
	//Custom Generation:
	public static CustomOreGeneration customOreGeneration = new CustomOreGeneration();
	
	//Gui IDs:
	public static final int guiIDGenerator = 0;
	public static final int guiIDAlloyFurnace = 1;
	
	//Proxies
	@SidedProxy(clientSide = "com.zyberholes.procon.proxy.ProxyClient", serverSide = "com.zyberholes.procon.proxy.ProxyCommon")
	public static ProxyCommon proxy;
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.registerTileEntities();
		
		itemBolt = new Bolt();
		itemScrewBase = new ScrewBase();
		itemScrewNut = new ScrewNut();
		itemIronNugget = new IronNugget();
		
		itemCopperIngot = new CopperIngot();
		itemTinIngot = new TinIngot();
		itemSilverIngot = new SilverIngot();
		itemTungstenIngot = new TungstenIngot();
		itemAluminiumIngot = new AluminiumIngot();
		
		oreChalcopyrite = new OreMineral("ore_chalcopyrite");
		oreCassiterite = new OreMineral("ore_cassiterite");
		oreArgentite = new OreMineral("ore_argentite");
		oreWolframite = new OreMineral("ore_wolframite");
		oreBauxite = new OreMineral("ore_bauxite");
		
		machineGeneratorIdle = new MachineGenerator("machine_generator_idle", false);
		machineGeneratorActive = new MachineGenerator("machine_generator_active", true);
		machineAlloyFurnaceIdle = new MachineAlloyFurnace("machine_alloy_furnace_idle", false);
		machineAlloyFurnaceActive = new MachineAlloyFurnace("machine_alloy_furnace_active", true);
		
		//Register Items:
		GameRegistry.registerItem(itemBolt, "itemBolt");
		GameRegistry.registerItem(itemScrewBase, "itemScrewBase");
		GameRegistry.registerItem(itemScrewNut, "itemScrewNut");
		GameRegistry.registerItem(itemIronNugget, "itemIronNugget");
		
		GameRegistry.registerItem(itemCopperIngot, "itemCopperIngot");
		GameRegistry.registerItem(itemTinIngot, "itemTinIngot");
		GameRegistry.registerItem(itemSilverIngot, "itemSilverIngot");
		GameRegistry.registerItem(itemTungstenIngot, "itemTungstenIngot");
		GameRegistry.registerItem(itemAluminiumIngot, "itemAluminiumIngot");
		
		//Register Blocks:
		GameRegistry.registerBlock(oreChalcopyrite, "blockChalcopyrite");
		GameRegistry.registerBlock(oreCassiterite, "blockCassiterite");
		GameRegistry.registerBlock(oreArgentite, "blockArgentite");
		GameRegistry.registerBlock(oreWolframite, "blockWolframite");
		GameRegistry.registerBlock(oreBauxite, "blockBauxite");
		
		GameRegistry.registerBlock(machineGeneratorIdle, "blockGeneratorIdle");
		GameRegistry.registerBlock(machineGeneratorActive, "blockGeneratorActive");
		GameRegistry.registerBlock(machineAlloyFurnaceIdle, "blockAlloyFurnaceIdle");
		GameRegistry.registerBlock(machineAlloyFurnaceActive, "blockAlloyFurnaceActive");
		
		//Register Generator:
		GameRegistry.registerWorldGenerator(customOreGeneration, 1);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		//Register Guihandler
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CustomGuiHandler());
		
		//Entities:
		EntityRegistry.registerModEntity(EntityBolt.class, "entityBolt", 4, this, 80, 3, true);
		proxy.registerRenderers();
		
		//Shaped Crafting:
		GameRegistry.addRecipe(new ItemStack(itemBolt, 2), new Object[] {"nnn", " b ", " b ", 'n', itemIronNugget, 'b', itemScrewBase});
		GameRegistry.addRecipe(new ItemStack(itemScrewBase, 2), new Object[] {"n ", " n", "n ", 'n', itemIronNugget});
		GameRegistry.addRecipe(new ItemStack(itemScrewBase, 2), new Object[] {" n", "n ", " n", 'n', itemIronNugget});
		GameRegistry.addRecipe(new ItemStack(itemScrewNut, 2), new Object[] {" nn", "n n", "nn ", 'n', itemIronNugget});
		GameRegistry.addRecipe(new ItemStack(itemScrewNut, 2), new Object[] {"nn ", "n n", " nn", 'n', itemIronNugget});
		GameRegistry.addRecipe(new ItemStack(Items.iron_ingot, 1), new Object[] {"iii", "iii", "iii", 'i', itemIronNugget});
		
		//Shapeless Crafting:
		GameRegistry.addShapelessRecipe(new ItemStack(itemIronNugget, 9), Items.iron_ingot);
		
		//Smelting:
		GameRegistry.addSmelting(itemBolt, new ItemStack(itemIronNugget, 3), 0F);
		GameRegistry.addSmelting(itemScrewBase, new ItemStack(itemIronNugget, 1), 0F);
		GameRegistry.addSmelting(itemScrewNut, new ItemStack(itemIronNugget, 3), 0F);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
}
