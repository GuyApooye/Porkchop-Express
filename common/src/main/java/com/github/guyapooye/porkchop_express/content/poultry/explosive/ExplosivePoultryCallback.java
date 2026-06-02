package com.github.guyapooye.porkchop_express.content.poultry.explosive;

import com.github.guyapooye.porkchop_express.content.poultry.PoultryBlockEntity;
import com.github.guyapooye.porkchop_express.content.poultry.PoultryCallback;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.physics.callback.BlockSubLevelCollisionCallback;
import dev.ryanhcode.sable.api.sublevel.ServerSubLevelContainer;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import dev.ryanhcode.sable.sublevel.system.SubLevelPhysicsSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;

public class ExplosivePoultryCallback extends PoultryCallback {
    public static final ExplosivePoultryCallback INSTANCE = new ExplosivePoultryCallback();
    
    @Override
    public double getTriggerVelocity() {
        return 12.0;
    }
    
    @Override
    public CollisionResult doOnCollide(
            BlockPos pos,
            Vector3d hitPos,
            double impactVelocity,
            ServerLevel level,
            PoultryBlockEntity blockEntity,
            ServerSubLevel subLevel
    ) {
        ExplosivePoultryBlockEntity explosivePoultry = (ExplosivePoultryBlockEntity) blockEntity;
        if (explosivePoultry.fuze < 0) {
            explosivePoultry.setFuze(20);
        }
        
        return super.doOnCollide(pos, hitPos, impactVelocity, level, blockEntity, subLevel);
    }
}
