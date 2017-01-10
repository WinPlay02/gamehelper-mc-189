package net.wfoas.gh.multipleworlds;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import com.google.gson.annotations.Expose;

public class TeleportPoint {

	protected int dim;

	protected float pitch;

	protected float yaw;

	protected double xd;

	protected double yd;

	protected double zd;

	@Expose(serialize = false)
	protected WorldServer world;

	// ------------------------------------------------------------

	public TeleportPoint(int dimension, double x, double y, double z, float playerPitch, float playerYaw) {
		this.dim = dimension;
		this.xd = x;
		this.yd = y;
		this.zd = z;
		this.pitch = playerPitch;
		this.yaw = playerYaw;
	}

	public TeleportPoint(WorldServer world, double x, double y, double z, float playerPitch, float playerYaw) {
		this.world = world;
		this.dim = world.provider.getDimensionId();
		this.xd = x;
		this.yd = y;
		this.zd = z;
		this.pitch = playerPitch;
		this.yaw = playerYaw;
	}

	public TeleportPoint(int dimension, BlockPos location, float pitch, float yaw) {
		this(dimension, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, pitch, yaw);
	}

	public TeleportPoint(Point point, int dimension, float pitch, float yaw) {
		this(dimension, point.getX(), point.getY(), point.getZ(), pitch, yaw);
	}

	public TeleportPoint(TeleportPoint point, float pitch, float yaw) {
		this(point.getDimension(), point.getX() + 0.5, point.getY(), point.getZ() + 0.5, pitch, yaw);
	}

	public TeleportPoint(Entity entity) {
		this(entity.worldObj instanceof WorldServer ? (WorldServer) entity.worldObj : null, entity.posX, entity.posY,
				entity.posZ, entity.rotationPitch, entity.rotationYaw);
	}

	public TeleportPoint(TeleportPoint point) {
		this(point.dim, point.xd, point.yd, point.zd, point.pitch, point.yaw);
	}

	// ------------------------------------------------------------

	public int getDimension() {
		return dim;
	}

	public double getX() {
		return xd;
	}

	public double getY() {
		return yd;
	}

	public double getZ() {
		return zd;
	}

	public int getBlockX() {
		return (int) Math.floor(xd);
	}

	public int getBlockY() {
		return (int) Math.floor(yd);
	}

	public int getBlockZ() {
		return (int) Math.floor(zd);
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setDimension(int dim) {
		this.dim = dim;
	}

	public WorldServer getWorld() {
		if (world == null || world.provider.getDimensionId() != dim)
			world = DimensionManager.getWorld(dim);
		return world;
	}

	public void setX(double value) {
		xd = value;
	}

	public void setY(double value) {
		yd = value;
	}

	public void setZ(double value) {
		zd = value;
	}

	public void setPitch(float value) {
		pitch = value;
	}

	public void setYaw(float value) {
		yaw = value;
	}

	// ------------------------------------------------------------

	/**
	 * Returns the length of this vector
	 */
	public double length() {
		return Math.sqrt(xd * xd + yd * yd + zd * zd);
	}

	/**
	 * Returns the distance to another point
	 */
	public double distance(TeleportPoint v) {
		return Math.sqrt((xd - v.xd) * (xd - v.xd) + (yd - v.yd) * (yd - v.yd) + (zd - v.zd) * (zd - v.zd));
	}

	/**
	 * Returns the distance to another entity
	 */
	public double distance(Entity e) {
		return Math.sqrt((xd - e.posX) * (xd - e.posX) + (yd - e.posY) * (yd - e.posY) + (zd - e.posZ) * (zd - e.posZ));
	}

	public void validatePositiveY() {
		if (yd < 0)
			yd = 0;
	}

	public Vec3 toVec3() {
		return new Vec3(xd, yd, zd);
	}

	public WorldPoint toWorldPoint() {
		return new WorldPoint(this);
	}

	// ------------------------------------------------------------

	@Override
	public String toString() {
		return "[" + xd + "," + yd + "," + zd + ",dim=" + dim + ",pitch=" + pitch + ",yaw=" + yaw + "]";
	}

	public String toReadableString() {
		return String.format("%.0f %.0f %.0f dim=%d", xd, yd, zd, dim);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof TeleportPoint) {
			TeleportPoint p = (TeleportPoint) object;
			return xd == p.xd && yd == p.yd && zd == p.zd;
		}
		if (object instanceof Point) {
			Point p = (Point) object;
			return (int) xd == p.getX() && (int) yd == p.getY() && (int) zd == p.getZ();
		}
		if (object instanceof WorldPoint) {
			WorldPoint p = (WorldPoint) object;
			return dim == p.getDimension() && (int) xd == p.getX() && (int) yd == p.getY() && (int) zd == p.getZ();
		}
		return false;
	}

	@Override
	public int hashCode() {
		int h = 1 + Double.valueOf(xd).hashCode();
		h = h * 31 + Double.valueOf(yd).hashCode();
		h = h * 31 + Double.valueOf(zd).hashCode();
		h = h * 31 + Double.valueOf(pitch).hashCode();
		h = h * 31 + Double.valueOf(yaw).hashCode();
		h = h * 31 + dim;
		return h;
	}

}