package com.zyberholes.procon.entities;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBolt extends EntityThrowable
{
	
	protected Item droppedItem;
	
	public EntityBolt(World aWorld) {
		super(aWorld);
	}
	
	public EntityBolt(World par0, EntityLivingBase par1) {
		super(par0, par1);
	}
	
	public EntityBolt(World par0, EntityLivingBase par1, Item par2) {
		super(par0, par1);
		droppedItem = par2;
	}
	
	public EntityBolt(World par0, double par1, double par2, double par3) {
		super(par0, par1, par2, par3);
	}
	
	
	@Override
	protected void onImpact(MovingObjectPosition hitPos) {
		
		if (hitPos.entityHit != null)
		{
			hitPos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
		}
		
		if (!this.worldObj.isRemote)
		{
			int x = (int)Math.floor(posX);
			int z = (int)Math.floor(posZ);
			
			Material groundMat = this.worldObj.getTopBlock(x, z).getMaterial();
			
			//kills item if it lands on organic floor:
			if (groundMat == Material.grass & this.rand.nextInt(3) == 0) {
				this.worldObj.playSoundAtEntity(this, "step.grass", 0.5F, 1F);
			}else if (groundMat == Material.ground & this.rand.nextInt(5) == 0) {
				this.worldObj.playSoundAtEntity(this, "step.gravel", 0.5F, 1F);
			}else if (groundMat == Material.plants & this.rand.nextInt(2) == 0) {
				this.worldObj.playSoundAtEntity(this, "step.grass", 0.5F, 1F);
			}else if (groundMat == Material.snow & this.rand.nextInt(2) == 0) {
				this.worldObj.playSoundAtEntity(this, "step.snow", 0.5F, 1F);
			}else {
				dropItem(droppedItem, 1);
			}
			
			this.setDead();
		}
	}
	
	
}
