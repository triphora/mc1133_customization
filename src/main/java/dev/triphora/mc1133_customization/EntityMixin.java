package dev.triphora.mc1133_customization;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Optional;

@Mixin(Entity.class)
public abstract class EntityMixin {
	/**
	 * @author triphora
	 * @reason Make the fix for MC-1133 only apply to slime blocks
	 */
	@Overwrite
	public void method_51703(boolean bl) {
		var that = (Entity) (Object) this;
		if (bl) {
			Box box = that.getBoundingBox();
			Box box2 = new Box(box.minX, box.minY - 1.0E-6, box.minZ, box.maxX, box.minY, box.maxZ);
			var block = that.world.method_51718(that, box2).map(blockPos -> (that.getWorld().getBlockState(blockPos)));
			if (block.isPresent()) {
				if (block.get().equals(Blocks.SLIME_BLOCK.getDefaultState())) {
					that.field_44784 = that.world.method_51718(that, box2);
				}
			}
		} else if (that.field_44784.isPresent()) {
			that.field_44784 = Optional.empty();
		}
	}
}
