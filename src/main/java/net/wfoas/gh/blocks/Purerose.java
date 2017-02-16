package net.wfoas.gh.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Purerose extends GameHelperModBlock {
	public Purerose() {
		super(Material.rock, "Purerose");
		this.setHarvestLevel("pickaxe", 3);
		this.setHardness(2f);
		this.setStepSound(Block.soundTypeStone);
}}
