# Les 3: Minecraft/Paper basics #3 #

In deze les kijken we oppervlakkig naar de implementatie van MySQL en databases binnen Java en Minecraft plugins.
We gaan portals maken om naar onze warps te teleporteren. En we gaan interactie doen met objecten en elementen in het spel. Door middel van events.

## Intro MySql ##
We beginnen met MySql, een database systeem. Een database is een plek waar je data kan opslaan. Je kan het zien als een soort Excel bestand. Je kan er data in opslaan. En je kan er data uit ophalen.
Vorige weken hebben we data opgeslagen in configs. Dit heeft zijn voor en nadelen. Een aantal hiervan zijn:
- Configs zijn makkelijk te delen met andere mensen
- Configs zijn makkelijk aan te passen
- Configs zijn makkelijk te backuppen
- Configs zijn makkelijk te lezen
- Configs zijn makkelijk te begrijpen
- Configs zijn makkelijk te gebruiken

Maar configs hebben ook een aantal nadelen:
- Configs zijn lokaal opgeslagen
- Configs zijn makkelijk te verliezen
- Configs zijn makkelijk te verwijderen
- Configs zijn makkelijk te veranderen

Voor nu gebruiken we configs. Maar onder andere bij grote servers is het handig om een database te gebruiken. Dit is omdat je dan makkelijk data kan delen tussen verschillende servers. En omdat je makkelijk backups kan maken. En omdat je makkelijk data kan opvragen en aanpassen.

## Events ##
In Paper hebben we de mogelijkheid gebruik te maken van events. Events zijn een manier om te reageren op acties van spelers. Bijvoorbeeld als een speler een blok kapot maakt. Of als een speler een blok plaatst.
Een aantal voorbeelden van veelgebruikte events zijn:
- PlayerJoinEvent
- PlayerQuitEvent
- PlayerInteractEvent
- PlayerMoveEvent
- BlockBreakEvent
- BlockPlaceEvent
- EntityDamageEvent
- EntityDamageByEntityEvent
- EntityDeathEvent
- InventoryClickEvent

### Warp portals ###
We gaan een portal maken om naar onze warps te teleporteren. Dit doen we door een portal te bouwen. En de blokken in de portal te herkennen. Als een speler in de portal staat teleporteren we hem naar de warp.
Om dit te kunnen doen hebben we een aantal dingen nodig:
- Een lijst van blocks binnen de portal
- Een target warp
- Een PlayerMoveEvent listener

#### Lijst van blocks binnen de portal ####
Bouw in-game een portal, net als een netherportal. Maar laat de binnenkant leeg.
We gaan nu een lijst maken van alle blokken binnen de portal.

Maak een nieuwe class aan. Noem deze `Portal.java`. Deze class gaan we gebruiken om de portal te herkennen. En om de portal te teleporteren.

In deze class maken we een variabele aan voor de target. En een lijst met blocks.
```java
@Getter
@Setter
@AllArgsConstructor
public class Portal {
    
    private Location target;
    private List<Location> blocks = new ArrayList<>();
    
}
```

We kunnen nu heel simpel een nieuw portaal aanmaken door het volgende stuk code to gebruiken.
```java
Portal portal = new Portal(
        new Location(Bukkit.getWorld("world"), 0, 100, 0),
        Arrays.asList(
            new Location(Bukkit.getWorld("world"), 0, 50, 0),
            new Location(Bukkit.getWorld("world"), 0, 50, 1),
            new Location(Bukkit.getWorld("world"), 0, 51, 0),
            new Location(Bukkit.getWorld("world"), 0, 51, 1),
            new Location(Bukkit.getWorld("world"), 0, 52, 0),
            new Location(Bukkit.getWorld("world"), 0, 52, 1)
        ));
```

Dit maakt een portaal naar de warp `spawn`.

#### PlayerMoveEvent listener ####
We gaan nu een listener maken voor het PlayerMoveEvent. Dit event wordt getriggerd als een speler beweegt. We gaan dit event gebruiken om te kijken of een speler in een portal staat. En als dat zo is teleporteren we hem naar de warp.

Maak een nieuwe class aan. Noem deze `PortalListener.java`. Deze class gaan we gebruiken om het event te luisteren. En om de speler te teleporteren.

In deze class maken we een variabele aan voor de portal. En een lijst met portals.
```java
@Getter
@Setter
public class PortalListener implements Listener {
    
    private List<Portal> portals = new ArrayList<>();
    
}
```

In deze lijst gaan we onze portals registreren. Wanneer een portaal geregistreerd is checken we of hier een speler in staat.

Eerst voegen we een register methode toe aan de `PortalListener`

```java
public void register(Portal portal) {
    portals.add(portal);
}
```

Nu gaan we de `PlayerMoveEvent` luisteren. Dit doen we door de volgende code toe te voegen aan de `PortalListener`
```java
@EventHandler
public void onMove(PlayerMoveEvent event){
    // De speler die beweegt
    Player player = event.getPlayer();

    // De locatie van de speler
    Location location = player.getLocation();

    // Loop door alle portals
    for (Portal portal : portals) {

        // Loop door alle blocks in de portal
        for (Location portalLocation : portal.getBlocks()) {

            // Check of de speler in de portal staat
            if (portalLocation.getBlockX() == location.getBlockX()
                && portalLocation.getBlockY() == location.getBlockY()
                && portalLocation.getBlockZ() == location.getBlockZ()
                && portalLocation.getWorld() == location.getWorld()) {
                //TODO: Teleporteer speler naar target
            }
        }
    }
}
```

Voeg zelf op de juiste plek de functionaliteit toe om de speler te teleporteren naar de target warp.

#### Listener registreren ####

Navigeer naar je Main class. Dit is de class die van het type JavaPlugin is. Dit is de class die de plugin representeert. Hier gaan we de listener registreren.

Voeg de volgende code toe aan de `onEnable` methode.
```java
PortalListener portalListener = new PortalListener();
Bukkit.getPluginManager().registerEvents(portalListener, this);
```

Dit zorgt ervoor dat Paper weet dat de `PortalListener` naar events moet luisteren.

#### Portal registreren ####
Nu kunnen we portals registreren. Dit doen we door een nieuwe portal te maken zoals hierboven aangegeven.
En deze te registreren bij de `PortalListener`. Dit doen we door de volgende code toe te voegen aan de `onEnable` methode.
```java
Portal portal = new Portal(
        new Location(Bukkit.getWorld("world"), 0, 100, 0),
        Arrays.asList(
            new Location(Bukkit.getWorld("world"), 0, 50, 0),
            new Location(Bukkit.getWorld("world"), 0, 50, 1),
            new Location(Bukkit.getWorld("world"), 0, 51, 0),
            new Location(Bukkit.getWorld("world"), 0, 51, 1),
            new Location(Bukkit.getWorld("world"), 0, 52, 0),
            new Location(Bukkit.getWorld("world"), 0, 52, 1)
        ));
portalListener.register(portal);
```

Zorg er wel voor dat je hier de juiste gegevens gebruikt voor jou portal.

De gegevens van je portal kan je vinden door in-game in de portal te gaan staan en daar op F3 te drukken. Dit laat een debug scherm zien. Met links boven je x, y, z coordinaten. Deze kan je gebruiken om de locaties van de portal te vinden.
De coordinaten die je ziet is de locatie van je voeten.

Bouw nu je plugin. En start je server. Als je nu in de portal gaat staan wordt je geteleporteerd naar de warp.

### EXTRA: Trampoline ###
We gaan een trampoline maken. Dit doen we door een blok te herkennen. En als een speler op dat blok springt hem de lucht in te schieten.

Om dit te kunnen doen hebben we een aantal dingen nodig:
- Een blok die we gaan herkennen als trampoline
- Een PlayerMoveEvent listener

#### PlayerMoveEvent Listener ####
We gaan nu een listener maken voor het PlayerMoveEvent. Dit event wordt getriggerd als een speler beweegt. We gaan dit event gebruiken om te kijken of een speler op een trampoline staat. En als dat zo is schieten we hem de lucht in.

Maak een nieuwe class aan. Noem deze `TrampolineListener.java`. Deze class gaan we gebruiken om het event te luisteren. Net zoals bij de portal.

```java
public class TrampolineListener implements Listener {
    
    private Material trampolineMaterial = Material.SLIME_BLOCK;
    
}
```

Hier zien we dat we een variabele hebben voor het trampoline blok. Dit is het blok dat we gaan herkennen als trampoline.
Ik heb hier voor slime gekozen omdat dat van nature al een blok is waar de speler op stuitert. Door dit te gebruiken zorgen we er automatisch voor dat de speler geen fall damage krijgt.

Nu gaan we de `PlayerMoveEvent` luisteren. Dit doen we door de volgende code toe te voegen aan de `TrampolineListener`
```java
@EventHandler
public void onMove(PlayerMoveEvent event) {
    // De speler die beweegt
    Player player = event.getPlayer();
    
    // De locatie van de speler -1 y as (1 blok onder de speler)
    Location location = player.getLocation().add(0, -1, 0);
    
    // Check of de speler op een trampoline staat
    if (location.getBlock().getType() == trampolineMaterial) {
        // Zet de velocity (momentum) van de speler naar x = 0, y = 2, z = 0
        
        player.setVelocity(new Vector(0,2,0));
    }
}
```

Kijk ook eens wat er gebeurt als je de waardes binnen de Vector iets veranderd.

#### Listener registreren ####
Om de listener te registreren gaan we opnieuw naar onze Main class.

Voeg de volgende code toe aan de `onEnable` methode.
```java
TrampolineListener trampolineListener = new TrampolineListener();
Bukkit.getPluginManager().registerEvents(trampolineListener, this);
```

Dit zorgt ervoor dat Paper weet dat de `TrampolineListener` naar events moet luisteren.