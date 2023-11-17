package me.aki.paper_autumn.utils;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class particleData {

    private static Map<UUID, Integer> COSMETICS = new HashMap<UUID, Integer>();
    private final UUID uuid;

    public particleData(UUID uuid){
        this.uuid = uuid;
    }

    public void setID(int id){
        COSMETICS.put(uuid, id);
    }

    public int getID(){
        return COSMETICS.get(uuid);
    }

    public boolean hasID(){
        if(COSMETICS.containsKey(uuid))
            return true;
        return false;
    }

    public void removeID(){
        COSMETICS.remove(uuid);
    }


    public void endTask(){
        if(getID() == 1)
            return;

        Bukkit.getScheduler().cancelTask(getID());
    }

    public static boolean hasFakeID(UUID uuid){
        if(COSMETICS.containsKey(uuid))
            if(COSMETICS.get(uuid) == 1)
                return true;
            return false;
    }

}
