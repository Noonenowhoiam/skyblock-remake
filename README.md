# Skyblock Remake

![Skyblock Remake Banner](https://github.com/Sweattypalms/skyblock-remake/blob/master/README/assets/banner.png?raw=true)

<b>Skyblock Remake </b> is an open-source fan project inspired by Hypixel's Skyblock. It aims not only to recreate the familiar experience but also to offer a platform for fellow developers to learn and grow.

🚨 **Disclaimer:** This project is a fan-made recreation of "Hypixel" Skyblock. It is not affiliated with or endorsed by Hypixel or its associated products. All rights and content belong to their respective owners.

## Why This Project Exists

I'm a high school kid, and I really enjoy programming, like you can make anything you want.
I got a taste of that when I started developing this plugin two years ago. It was pretty basic back then, 
but man, did it help me get good at programming, design patterns, efficient code, maths, everything. 
I feel like it would've helped me a lot if there were projects that I could've taken inspiration from, 
so I'm putting this out there so someone on the same path can benefit a lot from this. 
This is my way of paying it back, y'know. Enjoy! 

## Features

<details open>
<summary>Click to expand</summary>

### Player Features

-   **Stats System:** Complete stat system.
![Stats System](/README/assets/gameplay/stats_system.gif)
-   **Slayers (W.I.P):** Start quests and defeat bosses.
-   **Skills (W.I.P):** Progress and develop your player skills.
-   **Items System:** Dynamic system for in-game items.
-   **Regions:** Explore different areas and regions.
-   **Scoreboard:** See objectives, your balance and quests.
-   **Mobs System:** Engage with various in-game creatures.
-   **Ender Dragon Fight:** Battle the mighty Ender Dragon! Altar system with Custom Dragon Pathfinding + Dragon egg animation.

### Developer Features
<details>
<summary>Features with Code examples</summary>


-   **Annotation-Based Command System:** Efficiently handle and manage in-game commands.
```java
@Command(name = "example", description = "Example command", op = true)  
public void exampleCommand(Player player, String[] args) {  
  player.sendMessage(ChatColor.RED + "This is an example command!");  
    player.sendMessage(ChatColor.YELLOW + Strings.join(args, " "));  
}  
	  
@TabCompleter(command = "example")  
public List<String> exampleTabCompleter(Player player, String[] args) {  
  return List.of("example", "example2");  
}
```
-   **Hologram System:** Create both static and dynamic holograms with ease.
```java
Hologram hologram = new Hologram(  
		"Example Text",  
		new Location(Bukkit.getWorld("world"), 0, 100,0)  
);	
```
-   **Event-Based System:** Harness the power of events for versatile gameplay elements.
```java
@EventHandler  
public void onXpGain(SkyblockXpEvent event){  
	String name = event.getSkyblockPlayer().getPlayer().getName();  
	System.out.println(name + " gained " + event.getXp());  
}
```
-   **Particle Helpers:** Enhance visual elements with particle effects.
```java
Player player = ...;
// f (0.1) =>  Starting radius for the spiral.
// delta (1.5) =>  Max radius for the spiral
MathHelper.spiralParticles(player, 0.1, 1.5, Particle.FLAME);
```
-   **Auto Initializing:** Automatic setup for various modules including mobs, items, commands, and listeners.
-   **OOP-Based Systems:** Object-Oriented Programming based systems for items, mobs, and UIs.
```java
	/* Example Item */
public class LightningChestplate extends SkyblockItem implements IHasAbility, IDyedArmor {  
	public static final String ID = "lightning_chestplate";  
	private static final Map<Stats, Double> stats = new HashMap<>(Map.of(  
		Stats.HEALTH, 30d  
	));  
  
    public LightningChestplate() {  
		super(  
			ID,  
			"Lightning Armor Chestplate",  
			Material.LEATHER_CHESTPLATE,  
			null,  // Static lore
			stats,  
			Rarity.SPECIAL,  
			SkyblockItemType.CHESTPLATE  
		);  
    }  
  
	@Override  
	public List<Ability> getAbilities() {  
		return List.of(AbilityManager.LIGHTNING_ARMOR_ABILITY);  
	}  
  
	@Override  
	public String getHexColor() {  
		return "FFFF00";  
	}  
}
```
-   **UI System:** Robust UI system with callback features for clickable items and static GUIs.
```java
public class TestGUI extends BaseGUI {
	private static final int SIZE = 6 * 9; // 6 rows of 9 slots

	public TestGUI() {
		super(SIZE, "Test GUI");
	}

	@Override
	public void initializeItems(Player player){
		this.fillBorder(BorderType.ALL); // All around border
		ItemStack testItem = new ItemStack(Material.DIAMOND_SWORD);
		this.setItemAt(3, 4, testItem); // At (3,4)
		this.setNextItem(testItem); // Next available slot
	}
}
	
```
</details>

</details>

## Commands

<details>
<summary>Click to expand</summary>

### Admin Commands

-   `/mob <id>`
-   `/sitem <id>`
-   `/stat <stat_id> <amt>`
-   `/upgrade`
-   `/slayer_id`
-   `/?cancel_slayer`
-   `/sbrl`

### Player Commands

-   `/?slayer_gui`
-   `/hub`
-   `/test`

### Utils Commands

-   `/gms`
-   `/gmc`
-   `/gmss`
-   `/fix_inventory`

</details>


## Setup Guide

### Prerequisites

-   Multiverse Core
-   Plugin Manager (Optional: For reloading plugins)

1.  Download and install [IntelliJ IDEA](https://www.jetbrains.com/idea/download).
2.  Obtain Spigot's build tools for version 1.17.1 from [here](https://www.spigotmc.org/wiki/buildtools/).
3.  Edit the code as desired.
4.  Compile the code and place the output in your server's plugins directory.
5. Enjoy!
##### ---or---
1. Download the latest plugin's release from [here](https://github.com/Sweattypalms/skyblock-remake/releases/).
2. Place it in your server's plugin directory.
3. Enjoy!


## Contact or Support

-   For issues, queries, or suggestions, please use the project's [GitHub Issues](https://github.com/Sweattypalms/skyblock-remake/issues).
-   Join our Discord server for active discussions and updates: [Join Discord](https://discord.gg/Ew4u4TRbQ6).
-   For personal queries or urgent matters, you can contact me on Discord: `@sweattypalms`.

## Contribution

Contributions are welcome! If you're passionate about Skyblock and want to contribute to an open-source project, we'd love to have you onboard! Before creating a pull request, please ensure:

-   Your code is tested and working as expected.
-   Adherence to best coding practices and conventions.
-   I will review all code before merging to ensure quality and consistency.


## License

This project is licensed under the [GPLv3 License](LICENSE).
