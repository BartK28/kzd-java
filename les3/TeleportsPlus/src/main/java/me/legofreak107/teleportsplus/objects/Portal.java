package me.legofreak107.teleportsplus.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Portal {

    private Location target;
    private List<Location> blocks = new ArrayList<>();

}
