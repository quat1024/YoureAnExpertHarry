You're an Expert, Harry!
========================

The new age of expert modpacks has arrived!

To create your very own Expert Mode modpack:

* download *You're an Expert, Harry* and put it in your mods folder; launch the game once to generate a sample config file, `youre_an_expert_harry.json`
* tweak the settings to your liking
  * `goal_items`: An array of items players will have to craft to win your pack
  * `banned_items`: Items that will never appear in a recipe (unobtainable items)
  * `heck_methods`: Available recipes and the recipe levels they are applicable at
  * `top_difficulty`: The starting recipe level of the goal_items.
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
* Level 1 recipes are the last tier, so I stop there.

It shouldn't generate a cycle (since items from higher tiers are added to the "banned" list of lower tiers), but other than that, all bets are off. You might have to perform smelting recipes before you can craft a furnace, for example. Hope you found a blacksmith village house.

## List of Heck Methods

These are the recipe types. Why I called them "Heck Methods", well, the main function that generates zenscript files is called "Heck", and these are the methods employed by the heck function.

* `shapeless_2x2`: Shapeless 2x2 recipe with 4 random items
* `shaped_3x3`: Shaped 3x3 recipe with 9 random items
* `butterfly_shaped_3x3`: Vertically symmetrical 3x3 recipe with 6 random items
* `square_shaped_3x3`: Rotationally symmetric 3x3 with 3 random items
* `furnace`: Smelting recipe

More to come, incl. mod compat ones! Wow!

### License

Mozilla public license 2.0 or any later version

### Why?

FTB Odyssey