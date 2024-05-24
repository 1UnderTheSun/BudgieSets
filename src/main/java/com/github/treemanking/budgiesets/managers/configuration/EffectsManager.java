package com.github.treemanking.budgiesets.managers.configuration;

import com.github.treemanking.budgiesets.BudgieSets;
import com.github.treemanking.budgiesets.effects.EffectProcessor;
import com.github.treemanking.budgiesets.effects.EffectProcessorFactory;
import com.github.treemanking.budgiesets.managers.armorsets.ArmorSetListener;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The EffectsManager class is responsible for processing effects defined in the configuration file for a specific event.
 */
public class EffectsManager {

    /**
     * Constructs an EffectsManager to process effects for a specific event.
     *
     * @param eventMap the map representing the event and its associated effects
     * @param entity the player involved in the event
     * @param equipStatus the player's armor set equip status
     * @param event the event triggering the effects
     */
    public EffectsManager(Map<?, ?> eventMap, Entity entity, ArmorSetListener.EquipStatus equipStatus, Event event) {
        processEffectsMap(eventMap, entity, equipStatus, event);
    }

    /**
     * Processes the map containing effects for the event.
     *
     * @param eventMap the map representing the event and its associated effects
     * @param entity the player involved in the event
     * @param equipStatus the player's armor set equip status
     * @param event the event triggering the effects
     */
    private void processEffectsMap(Map<?, ?> eventMap, Entity entity, ArmorSetListener.EquipStatus equipStatus, Event event) {
        if (!eventMap.containsKey("Effects")) {
            throw new IllegalArgumentException("Effects key not found for an event.");
        }
        List<Map<?, ?>> effects = (List<Map<?, ?>>) eventMap.get("Effects");
        processEffects(effects, entity, equipStatus, event);
    }

    /**
     * Processes the list of effects for the event.
     *
     * @param effectsMap the list of maps representing individual effects
     * @param entity the player involved in the event
     * @param equipStatus the player's armor set equip status
     * @param event the event triggering the effects
     */
    private void processEffects(List<Map<?, ?>> effectsMap, Entity entity, ArmorSetListener.EquipStatus equipStatus, Event event) {
        if (effectsMap == null) {
            throw new IllegalArgumentException("Effects list not found or is null for an event.");
        }
        for (Map<?, ?> effect : effectsMap) {
            processEffect(effect, entity, equipStatus, event);
        }
    }

    /**
     * Processes an individual effect for the event.
     *
     * @param effectMap the map representing the individual effect
     * @param entity the player involved in the event
     * @param equipStatus the player's armor set equip status
     * @param event the event triggering the effect
     */
    private void processEffect(Map<?, ?> effectMap, Entity entity, ArmorSetListener.EquipStatus equipStatus, Event event) {
        if (effectMap == null) {
            return;
        }

        Set<? extends Map.Entry<?, ?>> entrySet = effectMap.entrySet();
        if (entrySet.size() != 1) {
            BudgieSets.getBudgieSets().getLogger().warning("Invalid effect structure found: " + effectMap);
            return;
        }

        Map.Entry<?, ?> entry = entrySet.iterator().next();
        String effectType = (String) entry.getKey();
        EffectProcessor processor = EffectProcessorFactory.createProcessor(effectType);

        if (processor == null) {
            BudgieSets.getBudgieSets().getLogger().warning("Invalid effect type: " + effectType);
            return;
        }

        Object value = entry.getValue();
        if (!(value instanceof List)) {
            BudgieSets.getBudgieSets().getLogger().warning("Invalid effect structure found: " + effectMap);
            return;
        }

        processor.processEffect((List<?>) value, entity, equipStatus, event);
    }
}
