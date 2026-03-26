![SSTKIT_SUPPORTS](https://cdn.modrinth.com/data/cached_images/109b788550ca83731761863e5f1c50639bc0d6d2.png)

**Superior Server Toolkit (SSTKIT) is designed for servers of all kind bringing in commands that freshen up the experience of the players on your server and especially making server management easier.
From basic commands like /warp and /heal and more commands that are necessary for protecting your server like /tempban and /vpncheck.**

[![Download on Modrinth](https://img.shields.io/badge/Download-Modrinth-1bd96a?style=for-the-badge&logo=modrinth&logoColor=white)](https://modrinth.com/project/FfmaCyRL)


## Config:

#### You can disable **ANY** Superior Server Toolkit commands (excluding aliases) under 'disabledFeatures.commands' in the config.yml as shown here.
Disabled commands will not show up for any players, requires restart after config (most other config adjustments can be reloaded with '/sstkitreload').

#### Config adjustments that don't change on /sstkitreload (require restart):
- disabledFeatures.commands
- scoreboard.show
- warp.pvpTimer
- tpa.pvpTimer
- home.pvpTimer
- home.useDiameter
- tpa.useDiameter

### Config Example (warps, homes, tempbans are set by commands):
```
VPNCHECK-iphubInfo-API_KEY: API_KEY_HERE

scoreboard:
  show: false
  serverName: "server_name"


vote:
  setup: true
  link: https://minecraft-server-list.com/server/510757/vote/

playerlist:
  opOnlyPerm: true

warp:
  setup: true
  opOnlyPerm: false
  pvpTimer: 15
  warps: {}

tpa:
  # useDiameter/2 = the minimum +- distance in the x and/or z position the command can be used
  useDiameter: 5000 # useDiameter/2 = the minimum +- distance in the x and/or z position the command can be used
  pvpTimer: 15

home:
  # useDiameter/2 = the minimum +- distance in the x and/or z position the command can be used
  useDiameter: 5000
  pvpTimer: 15
  locations: {}

supporter-chat:
  format: '<{playerName}> &a' # & = §
  players:
    - playerUUID # player 1's UUID
    - playerUUID # player 2's UUID

disabledFeatures:
  commands:
    - warp
    - warplist
    - warpremove
    - warpcreate
    #- any_sstkit_command

defaultBalance: 300
sellvalues:
  # Iron
  iron_ingot: 1
  iron_block: 18
```
