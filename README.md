You're an Expert, Harry!
========================
By the Spirit of Quat

Disclaimer: Quat1024 made this mod in its first iteration. (You can tell because this repo is a fork of his.) He has graciously given control and distribution of it to me (because he thinks it's a horrible idea ~and so do I tbh~).

The new age of expert modpacks has arrived!

To create your very own Expert Mode modpack:

* download *You're an Expert, Harry* and put it in your mods folder; launch the game once to generate a sample config file, `youre_an_expert_harry.json`.
  * The sample will (eventually) have more reasonable progression. Right now it just guarantees that the modpack is sane and (theoretically) completable.
* tweak the settings to your liking
  * `goal_items`: An array of items players will have to craft to win your pack
  * `banned_items`: Items that will never appear in a recipe (unobtainable items)
  * `base_items`: Items that will have their original crafting recipe (like iron ingots or gaia spirits), which the mod will use to craft items that wouldn't otherwise have a crafting recipe.
  * `heck_methods`: Available recipes and the recipe levels they are applicable at
  * `top_difficulty`: The starting recipe level of the goal_items.
  * For each goal, banned, and base item, you can add a tier number.
  * If you put a goal item in tier 2, the item may show up in recipes in tiers 3 and up, but it'll be craftable in tier 2.
  * If you put a banned item in tier 4, the item will be banned in tiers 4 and up and can't show up until a lower tier.
  * If you put a base item in tier 3, the item will be banned in tier 2 and 1, but it can be used in tiers 3 and up.
* run the game, open a world, and run the command `/youreanexpertharry` 
* *You're an Expert, Harry* will automatically generate .zs files in your scripts folder
* Relaunch the game to load your .zs files (make sure you have CraftTweaker)
* Congrats! Your Expert Mode modpack is now ready for distribution!

## How does it work?

*You're an Expert, Harry* starts at the items you specify in `goal_items` and works its way down, generating a tree of recipes downwards as it goes. It's easiest to explain by example:

* Let's say you have `minecraft:clay_ball` and `minecraft:beacon` as the entries in `goal_items`, `top_difficulty` is set to 3, and the only available recipe type for all levels is `shaped_3x3`.
* I choose a random recipe type for `minecraft:clay_ball` that is acceptable for level 3 recipes. I end up with `shaped_3x3` (since it's the only option), which calls for 9 items.
* I choose 9 random items to fill that crafting recipe with, and add it to the zenscript file.
* I do the same for `minecraft:beacon`. I choose `shaped_3x3` again which calls for another 9 items.
* I choose 9 more random items to fill that crafting recipe with, and add it to the zenscript file.
* Now I recurse. I look at the set of 18 items just used to craft level 3 recipes, and generate level 2 recipes for each one.
* I look at the set of 162 items just used to craft level 2 recipes, and generate level 1 recipes for each one.
* Level 1 recipes are the last tier, so I look at all the items needed and make recipes for them out of base items. (This prevents infinite loops if you pick good base items.)

It shouldn't generate a cycle (since items from higher tiers are added to the "banned" list of lower tiers), and you can use goal and banned items to create sanity checks so that you don't have to smelt items before you get a furnace.

## List of Heck Methods

These are the recipe types. Why I called them "Heck Methods", well, the main function that generates zenscript files is called "Heck", and these are the methods employed by the heck function.

* `shapeless_2x2`: Shapeless 2x2 recipe with 4 random items
* `shaped_3x3`: Shaped 3x3 recipe with 9 random items
* `butterfly_shaped_3x3`: Vertically symmetrical 3x3 recipe with 6 random items
* `square_shaped_3x3`: Rotationally symmetric 3x3 with 3 random items
* `furnace`: Smelting recipe

Botania:

* `mana_infusion`: One item -> one item with a random mana cost between that of manasteel and mana diamonds. No alchemy yet.

More to come iff you're interested.

### License

Mozilla public license 2.0 or any later version

### Why?

Quat: FTB Odyssey
Me: idek