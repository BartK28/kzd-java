# Les 4: Minecraft/Paper basics #3 #

In deze les kijken we oppervlakkig naar de implementatie van MySQL en databases binnen Java en Minecraft plugins.
We gaan portals maken om naar onze warps te teleporteren. En we gaan interactie doen met objecten en elementen in het spel. Door middel van events.

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

## Parkour ##
We gaan een parkour maken. Dit doen we door een parkour blok te herkennen. En zodra de speler op dat blok komt. Zijn y-positie op te slaan. Hierdoor kunnen we detecteren of de speler is gevallen, door te kijken wat het verschil is tussen de huidige positie en de laatste positie.

We gaan als blok een DIAMOND_BLOCK gebruiken. Als parkour start locatie gebruiken we een EMERALD_BLOCK.

Onze parkour plugin gaat als volgt werken:
- Als een speler op een EMERALD_BLOCK staat, wordt zijn positie opgeslagen. Dit is de startpositie. Hier teleporteren we de speler naartoe als hij/zij valt.
- Zodra de speler begint met het parkour wordt per DIAMOND_BLOCK waar de speler op staat zijn positie overschreven. Dit doen we zodat we altijd de laatste positie hebben.
- We vergelijken in de PlayerMoveEvent de afstand tussen de speler en het laatste DIAMOND_BLOCK. Op die manier kunnen we detecteren of de speler is gevallen.

### PlayerMoveEvent Listener ###
We gaan beginnen met het aanmaken van een PlayerMoveEvent. Dit event wordt getriggerd als een speler beweegt. We gaan dit event gebruiken om te kijken of een speler over de aangegeven blokken loopt.

Maak een nieuwe class aan. Noem deze `ParkourListener.java`. Deze class gaan we gebruiken om het event te luisteren.

```java
public class ParkourListener implements Listener {
    
    private Material parkourStartMaterial = Material.EMERALD_BLOCK;
    private Material parkourMaterial = Material.DIAMOND_BLOCK;
    
}
```

In deze class geven we gelijk 2 Materials aan. Dit zijn de blokken die we gaan herkennen. Deze mag je veranderen in andere blokken!

Nu gaan we naar het PlayerMoveEvent luisteren. Dit doen we door de volgende methode toe te voegen aan de `ParkourListener`
```java
@EventHandler
public void onMove(PlayerMoveEvent event) {
    // De speler die beweegt
    Player player = event.getPlayer();
    
    // De locatie van de speler -1 y as (1 blok onder de speler)
    Location location = player.getLocation().add(0, -1, 0);
}
```

Hier heb ik al wat code voorgetypt om de locatie van het blok, en de speler op te halen.

We voegen nu bovenaan de class. Onder de parkour blokken 2 nieuwe velden toe.
```java
private Location parkourStartLocation;
private Location parkourLocation;
```

Deze gaan we gebruiken om de locaties van de blokken op te slaan. Dit doen we in de `onMove` methode.
```java
// Check of de speler op een parkour blok staat
if (location.getBlock().getType() == parkourMaterial) {
    // Zet de parkour locatie naar de locatie van de speler
    if (parkourLocation == null || parkourLocation.getBlockY() != location.getBlockY() || parkourLocation.getBlockX() != location.getBlockX() || parkourLocation.getBlockZ() != location.getBlockZ()) {
        parkourLocation = location;
    } 
}
    
// Check of de speler op een parkour start blok staat
if (location.getBlock().getType() == parkourStartMaterial) {
    // Zet de parkour start locatie naar de locatie van de speler
    if (parkourStartLocation == null) {
        parkourStartLocation = player.getLocation();
    }
}
```

Voeg nu zelf een aantal leuke berichten toe. Gebruik hiervoor `player.sendMessage("bericht");`
Laat de speler bijvoorbeeld weten wanneer hij het parkour is begonnen.

Nu gaan we de afstand tussen de speler en het laatste parkour blok berekenen. Dit doen we door de volgende code toe te voegen aan de `onMove` methode.

```java
// Check of de speler het parkour is begonnen
if (parkourStartLocation == null) {
    // De speler is nog niet begonnen met het parkour
    return;
}

// Check of de speler al op een parkour blok is geweest
if (parkourLocation == null) {
    // De speler is nog niet op een parkour blok geweest
    return;
}

// Bereken de afstand tussen de speler en het laatste parkour blok
double distance = parkourLocation.distance(location);

// Check of de afstand groter is dan 6
if (distance > 6) {
    // Teleporteer de speler naar de start locatie
    player.teleport(parkourStartLocation);
    // Zet de parkour locatie naar null
    parkourLocation = null;
    // Zet de parkour startLocatie naar null
    parkourStartLocation = null;
}
```

Voeg ook hier zelf weer een bericht toe. Laat de speler bijvoorbeeld weten dat hij is gevallen.


### Listener registreren ###
Om de listener te registreren gaan we opnieuw naar onze Main class.

Voeg de volgende code toe aan de `onEnable` methode.
```java
ParkourListener parkourListener = new ParkourListener();
Bukkit.getPluginManager().registerEvents(parkourListener, this);
```

Dit zorgt ervoor dat Paper weet dat de `ParkourListener` naar events moet luisteren.

### Compilen ###
Compile je code door rechts boven op het groene pijltje te drukken. Als het goed is krijg je geen errors.
Je ziet nu in de code wat maven aan het doen is. Als alles gaat zoals het hoort, zie je daar een pad staan waar je plugin is opgeslagen.
Dit zier er ongeveer zo uit: `C:\Users\Bart Kouwenberg\IdeaProjects\Parkour\target\Parkour-1.0-SNAPSHOT.jar`
Navigeer naar deze locatie. En copy en paste je plugin naar de plugins folder van je server.
Start je server. En als het goed is werkt je plugin nu!

### Extra: Timer bijhouden ###
Als je nog iets extras wil doen is het leuk om een timer bij te houden. Dit doen we door een aantal dingen toe te voegen aan de `ParkourListener`.

We starten met een nieuw veld, een startTime. Dit veld is van het type long. En we noemen dit `startTime`.
```java
private long startTime;
```

Wanneer de speler het parkour start. Dus wanneer we de parkourStartLocation zetten. Zetten we ook de startTime. Dit doen we door de volgende code toe te voegen aan de `onMove` methode.
```java
startTime = System.currentTimeMillis();
```

We zetten hier de startTijd naar de huidige servertijd in ms.

Wanneer de speler aan het einde is gaan we de tijd berekenen die het heeft gekost voor de speler om het parkour te voltooien.

We starten met het aanmaken van een eind blok. Dit doen we door het volgende veld toe te voegen bovenaan.
```java
private Material parkourEndMaterial = Material.GOLD_BLOCK;
```

Nu gaan we detecteren of de speler op het eind blok staat. Dit doen we door de volgende code toe te voegen aan de `onMove` methode.
```java
// Check of de speler op een parkour eind blok staat
if (location.getBlock().getType() == parkourEndMaterial) {
    // Bereken de tijd die het heeft gekost om het parkour te voltooien
    long time = System.currentTimeMillis() - startTime;
    // Zet de parkour locatie naar null
    parkourLocation = null;
    // Zet de parkour startLocatie naar null
    parkourStartLocation = null;
    // Stuur de speler een bericht met de tijd die het heeft gekost om het parkour te voltooien
    player.sendMessage("Je hebt het parkour voltooid in " + time + "ms");
}
```

### Testen ###
Bouw nu zelf in je wereld een parkour en test deze werkt alles naar behoren?

## Trampoline ##
We gaan een trampoline maken. Dit doen we door een blok te herkennen. En als een speler op dat blok springt hem de lucht in te schieten.

Om dit te kunnen doen hebben we een aantal dingen nodig:
- Een blok die we gaan herkennen als trampoline
- Een PlayerMoveEvent listener

### PlayerMoveEvent Listener ###
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

### Listener registreren ###
Om de listener te registreren gaan we opnieuw naar onze Main class.

Voeg de volgende code toe aan de `onEnable` methode.
```java
TrampolineListener trampolineListener = new TrampolineListener();
Bukkit.getPluginManager().registerEvents(trampolineListener, this);
```

Dit zorgt ervoor dat Paper weet dat de `TrampolineListener` naar events moet luisteren.