![SSTKIT_SUPPORTS](https://cdn.modrinth.com/data/cached_images/109b788550ca83731761863e5f1c50639bc0d6d2.png)

**Superior Server Toolkit (SSTKIT) is designed for servers of all kind bringing in commands that freshen up the experience of the players on your server and especially making server management easier.
From basic commands like /warp and /heal and more commands that are necessary for protecting your server like /tempban and /vpncheck.**

[![Download on Modrinth](https://img.shields.io/badge/Download-Modrinth-1bd96a?style=for-the-badge&logo=modrinth&logoColor=white)](https://modrinth.com/project/FfmaCyRL)


## Config:

#### You can disable **ANY** Superior Server Toolkit commands (excluding aliases) under 'disabledFeatures.commands' in the config.yml as shown here.
Disabled commands will not show up for any players, requires restart after config (all other config adjustments can be reloaded with '/sstkitreload').

### Config Example (warps and tempbans set by commands):
```
VPNCHECK-iphubInfo-API_KEY: API_KEY_HERE

vote:
  setup: false
  link: https://minecraft-server-list.com/server/510757/vote/

playerlist:
  opOnlyPerm: true

warp:
  setup: false
  opOnlyPerm: false
  warps: {}

homes:
  locations: {}

disabledFeatures:
  commands:
    - warp
    - warplist
    - warpremove
    - warpcreate

sellvalues:
  # Iron
  iron_ingot: 1
  iron_block: 18
  iron_sword: 2
  iron_axe: 3
  iron_pickaxe: 3
  iron_hoe: 2
  iron_shovel: 1
  iron_helmet: 5
  iron_chestplate: 8
  iron_leggings: 7
  iron_boots: 4

  # Gold
  gold_ingot: 2
  gold_block: 18
  gold_sword: 4
  gold_axe: 6
  gold_pickaxe: 6
  gold_hoe: 4
  gold_shovel: 2
  gold_helmet: 10
  gold_chestplate: 16
  gold_leggings: 14
  gold_boots: 8

  # Diamond
  diamond: 10
  diamond_block: 90
  diamond_sword: 20
  diamond_axe: 30
  diamond_pickaxe: 30
  diamond_hoe: 20
  diamond_shovel: 10
  diamond_helmet: 50
  diamond_chestplate: 80
  diamond_leggings: 70
  diamond_boots: 40

  # Netherite
  netherite_ingot: 250
  netherite_block: 2250
  netherite_helmet: 300
  netherite_chestplate: 330
  netherite_leggings: 320
  netherite_boots: 290
  netherite_sword: 270
  netherite_axe: 280
  netherite_pickaxe: 280
  netherite_hoe: 270
  netherite_shovel: 260

  # Other ores
  emerald: 5
  emerald_block: 45
  lapis_lazuli: 3
  lapis_block: 27
  redstone: 2
  redstone_block: 18
  quartz: 2
  quartz_block: 8
  nether_quartz_ore: 5
  obsidian: 12
  ancient_debris: 50
  copper_ingot: 1
  copper_block: 9

  # Mob drops
  blaze_rod: 25
  ghast_tear: 80
  ender_pearl: 20
  shulker_shell: 50
  prismarine_crystals: 5
  prismarine_shard: 5
  slime_ball: 5
  wither_skeleton_skull: 150

  # Farming
  wheat: 1
  carrot: 1
  potato: 1
  beetroot: 1
  nether_wart: 2
  pumpkin: 2
  melon_slice: 1
  sugar_cane: 1
  cocoa_beans: 2
  egg: 1
  leather: 2
  rabbit_hide: 2
  wool: 2
  string: 2
  feather: 1
  bone: 1
  rotten_flesh: 1
  gunpowder: 3

  # Other
  elytra: 600
```
