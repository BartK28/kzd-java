# Les 3: Minecraft / Paper basics #2 #

## Vorige les ##
[Les 2: Minecraft / Paper basics #1](/les2/readme.md)

### Dit zou je gedaan moeten hebben / moeten weten ###
- Weten wat Minecraft is
- Weten welke Minecraft serveropties we hebben
- Minecraft plugin voor Intellij
- Paper server opzetten
- Eerste Minecraft plugin
- Plugin bouwen
- onCommand argumenten
- Teleportatie commando
- Kleurcodes in messages

## Deze les ##

### Leerdoelen ###
- Configs
- Warp plugin
- Geld bijhouden

### Uitleg ###
In deze les gaan we onszelf verdiepen in configs.
Configs zijn bedoeld om data op te slaan. Zo kunnen we bijvoorbeeld een lijst met warps opslaan.

Configs zijn in het geval van een Paper plugin, een yml bestand.

### Config aanmaken ###
Om een config aan te maken moet je eerst een bestand aanmaken. Dit doe je in de map resources. Naast de plugin.yml. 
Dit kan je doen door met de rechtermuisknop op de map te klikken en dan te kiezen voor `New > File`.
Geef het bestand een naam. Bijvoorbeeld `warps.yml`. Zorg dat je de extensie `.yml` gebruikt. Dit is een speciaal bestandstype dat Paper kan lezen.

Als je het bestand hebt aangemaakt kan je het openen. Je ziet dan een leeg bestand. Hierin kan je data opslaan.
Je kan bijvoorbeeld een lijst met warps opslaan. Of een lijst met spelers die een bepaalde rank hebben.

In dit geval heet het bestand `warps.yml`. Maar we kunnen natuurlijk ook andere namen gebruiken.

### Config laden ###
Om een config te laden moet je eerst een variabele aanmaken. Dit doe je door het volgende veld toe te voegen bovenaan je code:
```java
private FileConfiguration warpConfig;
```

Daarna moet je de config laden. Dit doe je in je onEnable. Je laadt de config door het volgende te typen:
```java
warpConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder() + "/warps.yml"));
```
Hier staat wederom warps.yml. Maar je kunt natuurlijk ook andere bestanden gebruiken.

### Config opslaan ###
Als je een config hebt geladen kan je er data in opslaan.

Data opslaan in een config is heel simpel. Paper heeft een voorgebouwde serializer en deserializer. 
Dit betekend dat je een object kan opslaan en weer kan ophalen.

Het Locatie object bijvoorbeeld. Wat we vorige week hebben gebruikt. 
Je kan een locatie opslaan in een config. En je kan een locatie weer ophalen uit een config.

Om een locatie op te slaan in een config gebruik je het volgende:

Hier hebben we het pad waar we de locatie op willen slaan. En de locatie variabele die we op willen slaan.
```java
config.set("warp.spawn", loc);
```

``warp.spawn`` wijst naar de key ``warp`` in de config en ``spawn`` is de key in de config waar de locatie op staat.
``loc`` is de locatie die we op slaan.

De config zal er nu zo uit komen te zien:
```yml
warp:
  spawn:
    world: world
    x: 0.0
    y: 0.0
    z: 0.0
    yaw: 0.0
    pitch: 0.0
```

#### Oefenopdracht: /setwarp commando ####
Maak een commando aan waarmee je een warp kan instellen. Je moet een naam meegeven. En de locatie waar de speler staat moet worden opgeslagen in de config.

Tips:
- Gebruik de `set` functie van de config om de locatie op te slaan.
- Gebruik de `getLocation` functie van de speler om de locatie op te halen.

Foutafhandeling:
- Als de warp geen naam heeft meegegeven moet je een bericht sturen naar de speler.

Een command aanmaken doen we op dezelfde manier als in de vorige les.
- Command aanmaken in de plugin.yml
- Command class aanmaken die de `onCommand` functie heeft en de CommandExecutor interface implementeert.
- Command Registeren in de onEnable functie

Gebruik de volgende code in je Main class om je config op te slaan:
```java
public void saveConfig(FileConfiguration config, String name) {
    File configFile = new File(getDataFolder() + "/" + name);
    try{
        if (!configFile.exists()) {
            getDataFolder().mkdirs();
            configFile.createNewFile();
        }
        config.save(configFile);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
```

Voorbeeldcode onCommand:
```java
@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    // Check of het commando argumenten heeft.
        
    // Cast de CommandSender naar player
    
    // Maak een locatie aan van de speler
    
    // Sla de locatie op in de config
        
    // Sla de config op
        
    // Stuur een bericht naar de speler
    
    // Return true zodat de server weet dat het commando is afgehandeld.
}
```

Sla de warp op onder de opgegeven naam. Dit doe je door een ``.`` te gebruiken als navigator.
Een warp met de naam spawn komt dus onder de key `warp.spawn` te staan.

Dit ziet er zo uit in je config:
```yml
warp:
  spawn:
    world: world
    x: 0.0
    y: 0.0
    z: 0.0
    yaw: 0.0
    pitch: 0.0
```

### Config uitlezen ###
Als je een config hebt geladen kan je er data uit ophalen.

#### Oefenopdracht: /warp commando ####
Maak een commando aan waarmee je naar een warp kan teleporteren. Je moet een naam meegeven. En de locatie die is opgeslagen in de config moet worden opgehaald.

Een command aanmaken doen we op dezelfde manier als in de vorige les.
- Command aanmaken in de plugin.yml
- Command class aanmaken die de `onCommand` functie heeft en de CommandExecutor interface implementeert.
- Command Registeren in de onEnable functie

Tips:
- Gebruik de `get` functie van de config om de locatie op te halen.
- Gebruik de `teleport` functie van de speler om de speler te teleporteren.

Foutafhandeling:
- Als de warp niet bestaat moet je een bericht sturen naar de speler.


## Geld bijhouden ##
Nu gaan we een config maken om de hoeveelheid geld van een speler bij te houden.

Maak een config aan met de naam `money.yml`. Sla deze op in de map resources.

Maak een variabele aan om de config in op te slaan. En laad de config.

Maak een functie aan om de hoeveelheid geld van een speler op te halen. Deze functie moet een speler als parameter hebben. En een int teruggeven.

Maak een functie aan om de hoeveelheid geld van een speler aan te passen. Deze functie moet een speler en een int als parameter hebben. En niks teruggeven.

Tips:
- Gebruik de `getFloat` functie van de config om de hoeveelheid geld op te halen.
- Gebruik de `set` functie van de config om de hoeveelheid geld aan te passen.
- Gebruik de `save` functie van de config om de config op te slaan.