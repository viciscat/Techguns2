package techguns.entities.projectiles;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.Techguns;
import techguns.api.damagesystem.DamageType;
import techguns.client.ClientProxy;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.items.guns.GenericGun;
import techguns.items.guns.IProjectileFactory;
import techguns.packets.PacketSpawnParticle;

public class DeatomizerProjectile extends GenericProjectile {

	public DeatomizerProjectile(World worldIn) {
		super(worldIn);
		if(worldIn.isRemote) {
			ClientProxy.get().createFXOnEntity("BlueBlasterTrail", this);
		}
	}

	public DeatomizerProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch,
			float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin,
			float penetration, boolean blockdamage, EnumBulletFirePos firePos) {
		super(worldIn, posX, posY, posZ, yaw, pitch, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration,
				blockdamage, firePos);
	}

	public DeatomizerProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread,
			float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage,
			EnumBulletFirePos firePos) {
		super(par2World, p, damage, speed, TTL, spread, dmgDropStart, dmgDropEnd, dmgMin, penetration, blockdamage, firePos);
	}

	@Override
	protected void doImpactEffects(Material mat, RayTraceResult rayTraceResult, SoundType sound) {
		/*if (!this.world.isRemote){
			double x = rayTraceResult.hitVec.x;
			double y = rayTraceResult.hitVec.y;
			double z = rayTraceResult.hitVec.z;

			TGPackets.network.sendToAllAround(new PacketSpawnParticle("BlueBlasterExplosion",x,y,z), TGPackets.targetPointAroundEnt(this, 32.0f));
		}*/
		Techguns.proxy.createFX("BlueBlasterExplosion", world, rayTraceResult.hitVec.x, rayTraceResult.hitVec.y, rayTraceResult.hitVec.z, 0,0,0);
	}

	@Override
	protected TGDamageSource getProjectileDamageSource() {
		TGDamageSource src = TGDamageSource.causeEnergyDamage(this, this.shooter, DeathType.GORE);
    	src.armorPenetration = this.penetration;
    	src.goreChance=1.0f;
    	return src;
	}

	public static class Factory implements IProjectileFactory<DeatomizerProjectile> {

		@Override
		public DeatomizerProjectile createProjectile(GenericGun gun, World world, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd,
				float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos firePos, float radius, double gravity) {
			return new DeatomizerProjectile(world,p,damage,speed,TTL,spread,dmgDropStart,dmgDropEnd,dmgMin,penetration,blockdamage,firePos);
		}

		@Override
		public DamageType getDamageType() {
			return DamageType.UNRESISTABLE;
		}

	}


}
