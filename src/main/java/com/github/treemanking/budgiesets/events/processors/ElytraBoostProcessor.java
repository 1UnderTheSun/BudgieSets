package com.github.treemanking.budgiesets.events.processors;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import com.github.treemanking.budgiesets.BudgieSets;
import com.github.treemanking.budgiesets.events.EventProcessor;
import com.github.treemanking.budgiesets.managers.armorsets.ArmorSetListener;
import com.github.treemanking.budgiesets.managers.configuration.EffectsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ElytraBoostProcessor implements EventProcessor {
    @Override
    public void process(Map<?, ?> effectsMap, BudgieSets plugin, HashMap<UUID, ArmorSetListener.EquipStatus> playerEquipStatusHashMap) {
        plugin.getServer().getPluginManager().registerEvents(new ElytraBoostProcessor.ElytraBoostListener(effectsMap, playerEquipStatusHashMap), plugin);
    }

    private class ElytraBoostListener implements Listener {
        private final Map<?, ?> effectsMap;
        private final Map<UUID, Long> cooldownMap = new HashMap<>();

        private final HashMap<UUID, ArmorSetListener.EquipStatus> playerEquipStatus;

        public ElytraBoostListener(Map<?, ?> event, HashMap<UUID, ArmorSetListener.EquipStatus> playerEquipStatusHashMap) {
            this.effectsMap = event;
            this.playerEquipStatus = playerEquipStatusHashMap;
        }

        @EventHandler
        private void onFishingHook(PlayerElytraBoostEvent playerElytraBoostEvent) {
            Player player = playerElytraBoostEvent.getPlayer();

            if (!playerEquipStatus.containsKey(player.getUniqueId())) return;
            ArmorSetListener.EquipStatus currentStatus = playerEquipStatus.get(player.getUniqueId());
            if (checkMap(effectsMap, player, cooldownMap)) {
                new EffectsManager(effectsMap, player, currentStatus, playerElytraBoostEvent);
            }        }
    }
}
