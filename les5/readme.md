# Les 5: Minecraft / Velocity #1 #

In deze les kijken we naar Velocity. Velocity is een proxy voor Minecraft. Dit betekend dat je met Velocity meerdere Minecraft servers kan verbinden. En dus een server netwerk kan maken. Velocity is een fork van BungeeCord. Velocity is een stuk sneller dan BungeeCord. En Velocity is makkelijker te gebruiken.

## Velocity netwerk opzetten en configureren ##
### Velocity proxy opzetten ###
We gaan eerst een Velocity netwerk opzetten. Dit doen we door een Velocity server te downloaden. Dit kan je doen op de [Velocity website](https://velocitypowered.com/downloads). Je kan de laatste versie downloaden. Maar je kan ook een versie kiezen. Als je een versie kiest moet je er wel op letten dat je de juiste versie van Velocity gebruikt. En dat je de juiste versie van Velocity gebruikt voor de versie van Minecraft die je wilt gebruiken.

Wij kiezen hier voor de laatste versie. Omdat we ook de laatste versie van paper gebruiken.

1. Maak onder je documenten een map genaamd kzd-java/netwerk. Hierin gaan we ons netwerk opslaan. Maak in deze netwerk map een map voor velocity. En plaats de velocity jar in deze map. Hernoem de jar naar server.jar.
2. Maak een start script aan. Dit doe je door met de rechtermuisknop op de map te klikken en dan te kiezen voor `New > Text Document`. Hernoem het bestand naar `start.bat`. Open het bestand en typ het volgende:
    ```batch
    java -Xms512M -Xmx512M -jar server.jar nogui
    ```
3. Start de server. En sluit deze wanneer hij volledig is opgestart.

### Hub server opzetten ###
Nu gaan we een hub server maken, een hub server dient als tussenserver tussen de andere servers. Ook wel een lobby genoemd. Hier kan je spelers heen sturen als ze inloggen. En vanuit hier kunnen ze naar andere servers gaan.

#### Hub server maken ####
Maak een map aan genaamd hub, en maak in deze map een server zoals je in [les 1](https://github.com/BartK28/kzd-java/blob/main/les2/readme.md#paper-server-opzetten) hebt gedaan.

#### Hub server configureren ####
1. Nu gaan we de hub server configureren. Open de server.properties en verander de server port naar 25566. Dit doen we omdat de velocity server op 25565 draait. En we willen niet dat de hub server op dezelfde poort draait. Als je dit niet doet kan je de hub server niet opstarten.
2. In de server.properties veranderen we ook de waarde `online-mode` naar `false` dit doen we omdat we anders niet kunnen inloggen op de server. Dit komt omdat de velocity server niet met een online mode server kan verbinden. En we willen dat de hub server met de velocity server verbind.
3. Open nu de map config en open het bestand `paper-global.yml` scroll naar ~93 en verander de waarde onder `proxies.velocity.enabled` naar `true`, `proxies.velocity.online-mode` naar `true` en vul hier het wachtwoord in wat onder je velocity map staat in het bestand `forwarding.secret` in onder `proxies.velocity.secret`.

#### Velocity configureren voor de hub ####
1. Open de map velocity in de netwerk map. Open het bestand `velocity.toml` en navigeer naar het blokje `[servers]` hier staan alle servers binnen je netwerk. Haal alle servers weg die voorgeconfigureerd staan. En registreer de hub door de volgende regel toe te voegen. `hub = "127.0.0.1:25566`. Deze regel is opgebouwd uit een `<naam> = "<ip>:<port>"`. Het ip `127.0.0.1` is het ip van de server (localhost). En de port `25566` is de port die we in de server.properties hebben ingesteld.
2. Verander nu het try blok onder de geregistreerde server naar het volgende:
    ```toml
    try = [
        "hub"
    ]
    ```
    Dit zorgt ervoor dat als een speler inlogt op de server hij/zij naar de hub wordt gestuurd. Dit hoef je ook enkel bij de hub te doen. Niet bij andere servers.
3. Verwijder onder het kopje `[forced-hosts]` de volgende code
    ```toml
   "lobby.example.com" = [
    "lobby"
    ]
    "factions.example.com" = [
    "factions"
    ]
    "minigames.example.com" = [
    "minigames"
    ]
   ```
4. Verander `player-info-forwarding-mode` naar `modern`
5. Verander als laatste de port onder `bind` naar `25565`. Dit is de port waarop de velocity server draait.

### Extra properties ###
Binnen in de `velocity.toml` hebben we nog een aantal configuratie opties.

Zoek in de config naar de motd. Dit is de MessageOfTheDay. Dit is de tekst die je ziet als je de server in de serverlijst ziet staan. Verander deze naar wat je wilt.

## Opdracht: Maak nu zelf een TNTRun server aan. En voeg deze toe aan je netwerk. ##
Serverinformatie:
- Server naam: TNTRun
- Server ip: 127.0.0.1
- Server port: 25567

Doe dit op dezelfde manier als de hub. Zonder de laatste stap met het try stuk.

## Hub plugins maken ##
Nu gaan we plugins maken voor de hub. Deze plugins zorgen ervoor dat spelers naar de juiste server worden gestuurd. En dat ze de juiste items krijgen.

1. Maak een nieuw project aan en noem deze `hub`.
2. Voeg lombok toe aan je pom.xml. Dit doe je door de volgende regels toe te voegen aan je pom.xml:
    ```xml
Inventory inventory = Bukkit.createInventory(null, 9, "Server Selector");
    ```
3. Maak een package aan genaamd `listeners`. Hier komen alle event listeners in. Zoals we vorige week hebben gedaan.

### PlayerJoinListener ###
We beginnen met een `PlayerJoinListener` deze gaat er voor zorgen dat wanneer een speler inlogt. Hij/zij naar de spawn wordt geteleporteerd. En een set met items krijgt.

Maak een nieuwe class aan genaamd `PlayerJoinListener`. Deze moet de `PlayerJoinEvent` implementeren. Dit doe je door de volgende regel toe te voegen aan de class declaratie: `implements PlayerJoinEvent`.

```java
public class PlayerJoinListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
    }
    
}
```

Hier zien we het `PlayerJoinEvent` in de methode onPlayerJoin. De `@EventHandler` annotatie geeft aan dat we hier een event handler hebben. En dat deze methode wordt uitgevoerd wanneer het event wordt getriggerd.

In die methode zien we dat we een `player` variabele hebben. Dit is de speler die zojuist de server is gejoined.

Registreer dit event in je Hub class. Dit doe je door de volgende regel toe te voegen aan de `onEnable` methode: `getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);`.

Wat we willen doen is de speler teleporteren naar de spawn.

1. Zoek een mooie spawn locatie op in je server. Druk op F3 en bekijk de coordinaten. Deze coordinaten gaan we gebruiken om de speler te teleporteren.
2. Maak een nieuwe `Location` aan. Dit doe je door de volgende regel toe te voegen: `Location location = new Location(player.getWorld(), x, y, z);`. Vervang `x`, `y` en `z` met de coordinaten van de spawn.
3. Teleporteer de speler naar de locatie. Dit doe je door de volgende regel toe te voegen: `player.teleport(location);`.

Wanneer we dit builden. En in de hub zetten zul je zien dat de speler wordt geteleporteerd naar de spawn.

Een leuke toevoeging is dat je in de PlayerJoinEvent ook de message aan kan passen.
Dit doe je door `event.setJoinMessage(msg);` toe te voegen.

Verander de join message naar iets leuks. Bijvoorbeeld: `event.setJoinMessage("§aWelkom op de server §7" + player.getName() + "§a!");`.

Nu we de speler hebben geteleporteerd. En een mooie message hebben. Gaan we de speler een aantal items geven.

Een item in Paper is opgebouwd uit een `ItemStack` en een `ItemMeta`. De `ItemStack` is het item zelf. En de `ItemMeta` is de data van het item. Zoals de naam, lore, enchantments, etc.

We beginnen met het aanmaken van een `ItemStack`. Dit doen we als volgt:
```java
ItemStack item = new ItemStack(Material.COMPASS, 1);
```

Hier maken we een `ItemStack` aan met het type `Material.COMPASS` en de hoeveelheid 1.

Nu we een `ItemStack` hebben. Gaan we de `ItemMeta` aanmaken. Dit doen we als volgt:
```java
ItemMeta meta = item.getItemMeta();
```

Nu we de `ItemMeta` hebben. Kunnen we de naam van het item veranderen. Dit doen we als volgt:
```java
meta.setDisplayName("§aServer Selector");
```

Nu we de naam hebben veranderd. Kunnen we de `ItemMeta` weer terug zetten op de `ItemStack`. Dit doen we als volgt:
```java
item.setItemMeta(meta);
```

Voordat we dit item aan de speler geven. Is het verstandig om de inventory te clearen. Dit doen we als volgt:
```java
player.getInventory().clear();
```

Nu is de speler zijn/haar inventory leeg. En kunnen we het item toevoegen. Dit doen we als volgt:
```java
player.getInventory().setItem(0, item);
```

Hier zien we 2 parameters, 0, en item. Item is het item wat we net hebben aangemaakt. En 0 is het slot waar we dit item willen hebben.
0 is helemaal links in de hotbar. En 8 helemaal rechts. Wanneer we hier dus 4 invullen. Zit onze server selector in het midden van de hotbar. Verander dit dus naar 4.

Nu we dit hebben gedaan. Kunnen we de plugin builden. En in de server zetten. Wanneer we nu inloggen. Zien we dat we een server selector hebben. En dat we naar de spawn worden geteleporteerd.

### ServerSelectorListener ###
Nu we de speler hebben geteleporteerd naar de spawn. En een server selector hebben. Gaan we er voor zorgen dat wanneer we op de server selector klikken. We een server kunnen kiezen.

Maak een nieuwe class aan genaamd `ServerSelectorListener`. Deze moet de `PlayerInteractEvent` implementeren. Dit doe je door de volgende regel toe te voegen aan de class declaratie: `implements PlayerInteractEvent`.

```java
public class ServerSelectorListener implements Listener {
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
    }
    
}
```

Hier zien we het `PlayerInteractEvent` in de methode onPlayerInteract. De `@EventHandler` annotatie geeft aan dat we hier een event handler hebben. En dat deze methode wordt uitgevoerd wanneer het event wordt getriggerd.

In die methode zien we dat we een `player` variabele hebben. Dit is de speler die interact in of met de wereld.

Registreer dit event in je Hub class. Dit doe je door de volgende regel toe te voegen aan de `onEnable` methode: `getServer().getPluginManager().registerEvents(new ServerSelectorListener(), this);`.

Wat we willen doen is kijken of de speler met een item in zijn/haar hand interact. En of dit item een server selector is.

Kijk of de speler een item in zijn/haar hand heeft. Dit doe je als volgt:
```java 
if (player.getItemInHand() == null) return;
```

Kijk of het item in zijn/haar hand een server selector is. Dit doe je als volgt:
```java
if (!player.getItemInHand().getType().equals(Material.COMPASS)) return;
```

Hier zien we op beide plekken een if statement met een return. Dit zorgt er voor dat wanneer de speler geen item in zijn/haar hand heeft. Of het item in zijn/haar hand geen server selector is. De code stopt met uitvoeren.

Verder hebben we in de interact event de mogelijkheid het event te cancellen. Wanneer we dit doen. Zullen alle interacties die de speler maakt. Niet worden opgenomen in de server.
Spelers hebben dan niet meer de mogelijkheid om te bouwen/slopen. En kunnen ook niet meer met andere spelers, of dingen zoals buttons en deuren interacten.

Als je dit wil doen plaats je bovenaan de methode de volgende regel: `event.setCancelled(true);`.

LET OP: Je kan dan zelf ook niet meer bouwen of slopen. Zorg er dus voor dat je dit alleen doet wanneer het nodig is.

TIP: Omring deze setCancelled methode door een if statement met player.isOp, of player.hasPermission. Op deze manier kan je jezelf buitensluiten van deze cancel.

Nu we weten dat de speler een server selector in zijn/haar hand heeft. Kunnen we beginnen met het maken van een inventory. Dit doen we als volgt:
```java
Inventory inventory = Bukkit.createInventory(null, 9, "Server Selector");
```

Hier maken we een inventory aan met 9 slots. En de naam "Server Selector".

Nu we de inventory hebben. Kunnen we beginnen met het toevoegen van de servers. Dit doen we als volgt:
```java
ItemStack tntrun = new ItemStack(Material.TNT, 1);
ItemMeta tntrunMeta = tntrun.getItemMeta();
tntrunMeta.setDisplayName("§aTNT Run");
tntrun.setItemMeta(tntrunMeta);

inventory.setItem(4, tntrun);
```

Hier hebben we een Item aangemaakt met de volgende waarde:
- Type: TNT
- Hoeveelheid: 1
- Naam: TNT Run
- Slot: 4

Nu we dit hebben gedaan. Kunnen we de inventory openen voor de speler. Dit doen we als volgt:
```java
player.openInventory(inventory);
```

Nu we dit hebben gedaan. Kunnen we de plugin builden. En in de server zetten. Wanneer we nu op de server selector klikken. Zien we dat we een inventory krijgen met een TNT Run item.

Echter doet dit nog niks. Als we hierop klikken, kunnen we dit item gewoon uit de inventory halen.

### ServerSelectorClickListener ###

Nu we een inventory hebben. En we weten wanneer de speler op de server selector klikt. Gaan we er voor zorgen dat wanneer de speler op een item in de inventory klikt. Hij/zij naar de juiste server wordt geteleporteerd.

Maak een nieuwe class aan genaamd `ServerSelectorClickListener`. Deze moet de `InventoryClickEvent` implementeren. Dit doe je door de volgende regel toe te voegen aan de class declaratie: `implements InventoryClickEvent`.

```java
public class ServerSelectorClickListener implements Listener {
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
    }
    
}
```

Hier zien we het `InventoryClickEvent` in de methode onInventoryClick. De `@EventHandler` annotatie geeft aan dat we hier een event handler hebben. En dat deze methode wordt uitgevoerd wanneer het event wordt getriggerd.

In die methode zien we dat we een `player` variabele hebben. Dit is de speler die interact met een inventory.

Registreer dit event in je Hub class. Dit doe je door de volgende regel toe te voegen aan de `onEnable` methode: `getServer().getPluginManager().registerEvents(new ServerSelectorClickListener(), this);`.

Wat we willen doen is kijken of de speler op een item in de inventory klikt. En of dit item een server is.

Kijk of de speler op een item in de inventory klikt. Dit doe je als volgt:
```java
if (event.getCurrentItem() == null) return;
```

Kijk of het item in de inventory een ItemMeta heeft. Dit doe je als volgt:
```java
if (!event.getCurrentItem().hasItemMeta()) return;
```

Kijk of het item in de inventory een naam heeft. Dit doe je als volgt:
```java
if (!event.getCurrentItem().getItemMeta().hasDisplayName()) return;
```

Kijk of het item in de inventory de tntrun server is. Dit doe je als volgt:
```java
if (!event.getCurrentItem().getItemMeta().getDisplayName().contains("TNT Run")) return;
```

Hier zien we op alle plekken een if statement met een return. Dit zorgt er voor dat wanneer de speler niet op een item in de inventory klikt. Of het item in de inventory geen server is. De code stopt met uitvoeren.

Ook hier hebben we weer een setCancelled methode. Deze zorgt er voor dat de interactie van de speler niet wordt opgenomen in de server. cancel deze methode onderaan de ifs. Dus enkel wanneer het item een tnt run item is.

Nu we weten dat de speler op een server in de inventory klikt. Kunnen we beginnen met het teleporteren van de speler. Dit doen we als volgt:

Om dit te doen gaan we naar de Main class. Dit is de class die de plugin start. En waar we de event listeners registreren. In deze class gaan we een nieuwe methode aanmaken. Deze methode gaan we aanroepen wanneer we de speler naar een andere server willen sturen.

Maak een nieuwe methode aan genaamd `sendPlayerToServer`. Deze moet de `Player` en `String` parameters hebben. Dit doe je door de volgende regel toe te voegen aan de methode declaratie: `(Player player, String server)`.

```java


public void sendPlayerToServer(Player player, String name) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(name);

        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
}

```

Deze methode zorgt ervoor dat je de velocity kan benaderen en kan vragen of een speler naar een andere server kan worden gestuurd.

Nu we deze methode hebben, hoeven we deze alleen nog maar aan te roepen. Maar voordat we dit doen moeten we een kanaal registreren in de velocity.

Dit doen we door in de onEnable een pluginChannel te registreren:
```java
getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
```

Wanneer de plugin channel is geregistreerd. Gaan we in de Main class een static instance aanmaken. Dit doen we als volgt:
```java
@Getter
private static Hub instance;
```

Als je dit hebt gedaan moet je de instance variabele vullen in de onEnable. Dit doe je als volgt:
```java
instance = this;
```

Nu we dit hebben gedaan. Kunnen we de methode aanroepen in de ServerSelectorClickListener class. Dit doen we als volgt:
```java
Hub.getInstance().sendPlayerToServer(player, "tntrun");
```

Nu we dit hebben gedaan. Kunnen we de plugin builden. En in de server zetten. Wanneer we nu op de server selector klikken. Zien we dat we een inventory krijgen met een TNT Run item.

Wanneer we hierop klikken worden we naar de TNT Run server gestuurd.

## Opdracht: Cancel EntityDamageEvent ##
Wanneer je in de lobby bent. En je springt van een hoge plek. Dan krijg je fall damage. Dit willen we niet. We willen dat de speler geen damage krijgt.

Maak een nieuwe class aan genaamd `EntityDamageListener`. Deze moet de `EntityDamageEvent` implementeren. Dit doe je door de volgende regel toe te voegen aan de class declaratie: `implements EntityDamageEvent`.

Registreer dit event. En cancel het.

## Leuk extratje ##
Voeg de trampoline functie van vorige les ook toe aan je hub plugin. Om je lobby een beetje aan te kleden!
   

