![SSTKIT_SUPPORTS](https://cdn.modrinth.com/data/cached_images/109b788550ca83731761863e5f1c50639bc0d6d2.png)

**Superior Server Toolkit (SSTKIT) is designed for servers of all kind bringing in commands that freshen up the experience of the players on your server and especially making server management easier.
From basic commands like /warp and /heal and more commands that are necessary for protecting your server like /tempban and /vpncheck.**

[![Download on Modrinth](https://img.shields.io/badge/Download-Modrinth-1bd96a?style=for-the-badge&logo=modrinth&logoColor=white)](https://modrinth.com/project/FfmaCyRL)

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

disabledFeatures:
  commands:
    - warp
    - warplist
    - warpremove
    - warpcreate
```
#### You can dsiable **ANY** Superior Server Toolkit commands (excluding aliases) under 'disabledFeatures.commands' in the config.yml as shown here. 
Disabled commands will not show up for any players, requires restart after config (all other config adjustments can be reloaded with '/sstkitreload').
