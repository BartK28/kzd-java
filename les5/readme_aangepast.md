# Les 5: Schapen schieten #

Vandaag gaan we een schapen-schiet-stok maken. Dit is een stok waarmee je schapen kan schieten.

## Aanmaken van de listener ##
Maak een nieuwe package aan genaamd ``listeners`` en maak hierin een nieuwe class aan genaamd ``SheepShootListener``. Deze class moet de ``Listener`` interface implementeren. Dit doe je door ``implements Listener`` achter de class naam te zetten. Je class ziet er nu zo uit:

```java
public class MobShootListener implements Listener {

}
```

## De event handler ##
Nu gaan we de event handler maken. Dit is een method die wordt aangeroepen als er een event plaatsvindt. In dit geval willen we dat de method wordt aangeroepen als een speler een item in zijn hand gebruikt. Dit doen we door de ``@EventHandler`` annotation boven de method te zetten. De method moet ook een ``PlayerInteractEvent`` parameter hebben. Dit is het event dat wordt aangeroepen als een speler een item in zijn hand gebruikt. De method ziet er nu zo uit:

```java
@EventHandler
public void onShoot(PlayerInteractEvent event) {
    
}
```

### Checken of de interactie `RIGHT_CLICK_AIR` is ###
Nu gaan we checken of de interactie die de speler heeft gedaan ``RIGHT_CLICK_AIR`` is. Dit doen we door de ``getAction()`` method van het event aan te roepen. Deze method geeft een ``Action`` terug. Deze ``Action`` kan ``RIGHT_CLICK_AIR``, ``RIGHT_CLICK_BLOCK``, ``LEFT_CLICK_AIR`` of ``LEFT_CLICK_BLOCK`` zijn. We willen alleen iets doen als de ``Action`` ``RIGHT_CLICK_AIR`` is. Dit doen we met een ``if`` statement. De method ziet er nu zo uit:

```java
@EventHandler
public void onShoot(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_AIR) {
        return;
    }
}
```

We returnen hier zodra het niet RIGHT_CLICK_AIR is, dit betekend dat de rest van de method niet wordt uitgevoerd als het niet RIGHT_CLICK_AIR is.

### Checken of de speler een stok in zijn hand heeft ###
Nu gaan we checken of de speler een stok in zijn hand heeft. Dit doen we door de ``event.getPlayer().getInventory().getItemInMainHand()`` method van het event aan te roepen. Deze method geeft een ``ItemStack`` terug. Deze ``ItemStack`` is het item dat de speler in zijn hand heeft. We willen alleen iets doen als de ``ItemStack`` een stok is. Dit doen we met een ``if`` statement. De method ziet er nu zo uit:

```java
@EventHandler
public void onShoot(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_AIR) {
        return;
    }
    
    if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.STICK) {
        return;
    }
}
```

We returnen hier zodra het geen stok is, dit betekend dat de rest van de method niet wordt uitgevoerd als het geen stok is.

### Het schaap spawnen ###
Nu gaan we het schaap spawnen. Dit doen we door een ``Entity`` aan te maken. Dit doen we door de ``event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.SHEEP)`` method aan te roepen. Deze method geeft een ``Entity`` terug. Deze ``Entity`` is het schaap dat we gaan spawnen. We willen het schaap ook een kleur geven. Dit doen we door de ``setColor(Color.RED)`` method van de ``Entity`` aan te roepen. De method ziet er nu zo uit:

```java
@EventHandler
public void onShoot(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_AIR) {
        return;
    }
    
    if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.STICK) {
        return;
    }
    
    Sheep sheep = event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.SHEEP);
    sheep.setColor(Color.RED);
}
```

### Het schaap laten schieten ###
Nu gaan we het schaap laten schieten. Dit doen we door de ``setVelocity(event.getPlayer().getLocation().getDirection().multiply(2))`` method van de ``Entity`` aan te roepen. Deze method geeft een ``void`` terug. Deze ``void`` is niks. De method ziet er nu zo uit:

```java
@EventHandler
public void onShoot(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_AIR) {
        return;
    }
    
    if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.STICK) {
        return;
    }
    
    Sheep sheep = (Sheep)event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.SHEEP);
    sheep.setColor(DyeColor.RED);
    sheep.setVelocity(event.getPlayer().getLocation().getDirection().multiply(2));
}
```

### De listener registreren ###
Nu hoeven we alleen de method nog te registreren. Dit doen we door in je Main class de ``getServer().getPluginManager().registerEvents(new SheepShootListener(), this)`` method aan te roepen. Deze method geeft een ``void`` terug. Deze ``void`` is niks. Je Main class ziet er nu zo uit:

```java
@Override
public void onEnable() {
    getServer().getPluginManager().registerEvents(new SheepShootListener(), this);
}
```

## Leuke extras ##
- Geef de schapen een random kleur.
- Geef de schapen random de nametag ``Dinnerbone``.
- Geef de schapen random de nametag ``Jeb_``.