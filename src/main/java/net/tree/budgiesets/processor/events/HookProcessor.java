package net.tree.budgiesets.processor.events;

import net.tree.budgiesets.BudgieSets;
import net.tree.budgiesets.eventlisteners.ArmorSetListener;
import net.tree.budgiesets.managers.EffectsManager;
import net.tree.budgiesets.processor.interfaces.EventProcessor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HookProcessor implements EventProcessor {
    @Override
    public void process(Map<?, ?> effectsMap, BudgieSets plugin, HashMap<UUID, ArmorSetListener.EquipStatus> playerEquipStatusHashMap) {
        plugin.getServer().getPluginManager().registerEvents(new HookProcessor.HookListener(effectsMap, playerEquipStatusHashMap), plugin);
    }

    private static class HookListener implements Listener {

        private final Map<?, ?> effectsMap;
        private final HashMap<UUID, ArmorSetListener.EquipStatus> playerEquipStatus;

        public HookListener(Map<?, ?> event, HashMap<UUID, ArmorSetListener.EquipStatus> playerEquipStatusHashMap) {
            this.effectsMap = event;
            this.playerEquipStatus = playerEquipStatusHashMap;
        }

        @EventHandler
        private void onFishingHook(PlayerFishEvent fishEvent) {
            Player player = fishEvent.getPlayer();

            if (!playerEquipStatus.containsKey(player.getUniqueId())) return;
            ArmorSetListener.EquipStatus currentStatus = playerEquipStatus.get(player.getUniqueId());
            new EffectsManager(effectsMap, player, currentStatus, fishEvent);
        }
    }
}
