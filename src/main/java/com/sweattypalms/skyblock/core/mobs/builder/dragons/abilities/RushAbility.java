package com.sweattypalms.skyblock.core.mobs.builder.dragons.abilities;

import com.sweattypalms.skyblock.core.helpers.EntityHelper;
import com.sweattypalms.skyblock.core.helpers.MathHelper;
import com.sweattypalms.skyblock.core.helpers.PlaceholderFormatter;
import com.sweattypalms.skyblock.core.mobs.builder.dragons.DragonStage;
import com.sweattypalms.skyblock.core.mobs.builder.dragons.EnderDragon;
import com.sweattypalms.skyblock.core.player.SkyblockPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class RushAbility implements IDragonAbility{

    private final EnderDragon dragon;
    private int tickCount = 0;

    public RushAbility(EnderDragon dragon) {
        this.dragon = dragon;
    }


    Player target = null;
    @Override
    public void start() {
        this.dragon.setMoving(false);
        this.target = EntityHelper.getClosestPlayer((LivingEntity) this.dragon.getBukkitEntity());
        rushing = true;
        startRushPosition = this.dragon.getBukkitEntity().getLocation().toVector();
        rushProgress = 0.0d;
    }

    boolean rushing = false;
    Vector startRushPosition = null;
    double rushProgress = 0.0d;
    @Override
    public void stop() {
//        dragon.setMoving(true); => will be handled by the tick method
        dragon.setAbility(null);
        tickCount = 0;
    }

    @Override
    public void tick() {
        if(this.target == null){
            throw new NullPointerException("Target not found??? cancelling ability");
        }

        Vector nextPosition = MathHelper.linearInterpolation(startRushPosition, target.getLocation().toVector(), rushProgress);
        this.dragon.setPositionRotation(nextPosition.getX(), nextPosition.getY(), nextPosition.getZ(), target.getLocation().getYaw(), target.getLocation().getPitch());

        rushProgress += 0.025;

        if(rushProgress >= 1 || this.dragon.getBukkitEntity().getLocation().distance(target.getLocation()) < 3){
            String message = "$5☬ $c" + dragon.getDragonName() + " $dused $eRush $don you for $c" + PlaceholderFormatter.formatDecimalCSV(dragon.getDragonDamage()) + " damage!";
            message = PlaceholderFormatter.format(message);
            target.sendMessage(message);
            SkyblockPlayer.getSkyblockPlayer(target).damageWithReduction(dragon.getDragonDamage());
            computeReturnPath();
        }
    }

    private void computeReturnPath(){
        Vector endPosition = dragon.getCurrentStage().getPoint(1);
        Vector currentDragonPosition = this.dragon.getBukkitEntity().getLocation().toVector();

        dragon.setCurrentStage(new DragonStage(List.of(currentDragonPosition, endPosition), 0.02));
dragon.setT(0.0);
        dragon.setMoving(true);
        dragon.setAbility(null);
        rushing = false;
    }

    @Override
    public boolean shouldActivate() {
        return dragon.getRandom().nextInt(500) == 0; // 1 in 1500 chance or every 75 seconds
    }

    //    private void executeRush() {
//        if (isDoingAbility) return;
//
//        isDoingAbility = true;
//        moving = false;
//        this.target = EntityHelper.getClosestPlayer((LivingEntity) this.getBukkitEntity());
//
//        if (target == null) {
//            throw new IllegalStateException("No target found??");
//        }
//
//        this.isRushing = true;
//        startRushPosition = this.getBukkitEntity().getLocation().toVector();
//        rushProgress = 0.0d;
//    }
//
//    private void rushTowardsPlayer() {
//        // Calculate the next position using LERP
//        Vector nextPosition = MathHelper.lerp(startRushPosition, target.getLocation().toVector(), rushProgress);
//        Location nextLoc = nextPosition.toLocation(this.getBukkitEntity().getWorld());
//
//        // Set the position and look direction towards the player
//        this.setPositionRotation(nextLoc.getX(), nextLoc.getY(), nextLoc.getZ(), target.getLocation().getYaw(), target.getLocation().getPitch());
//
//        rushProgress += 0.025;  // Adjust this value to control the speed
//
//        if (rushProgress >= 1 || this.getBukkitEntity().getLocation().distance(target.getLocation()) < 3) {
//            String message = "$5☬ $c" + getDragonName() + " $dused $eRush $don you for $c" + getDragonDamage() + " damage!";
//            message = PlaceholderFormatter.format(message);
//            target.sendMessage(message);
//            SkyblockPlayer.getSkyblockPlayer(target).damageWithReduction(getDragonDamage());
//            computeReturnPath();
//        }
//    }
//
//    private void computeReturnPath() {
//        Vector endPosition = currentStage.getPoint(1); // Get  the end position from the current path
//        Vector currentDragonPosition = this.getBukkitEntity().getLocation().toVector();
//
//        List<Vector> path = List.of(currentDragonPosition, endPosition);
//        DragonStage returnPath = new DragonStage(path, 0.02);
//
//        // Reset it so it starts lerping from the start of y = mx + n
//        t = 0.0;
//        currentStage = returnPath;
//
//
//        // To return to the main path
//        isRushing = false;
//        moving = true;
//    }


}
