# Version 2.1.0
- Added the liquid-fueled drill and various config values to configure it
- Gave the Seismic Reader a crafting recipe
- Updated the drill's 3D model
- Added a progress bar to the miner's GUI showing the progress on the block that is being currently mined
- Fixed an exploit where exchanging the drill head in a running drill to a head with a lower mining tier than required to mine the ore the drill is currently mining would keep using power and playing the rotation animations though not actually mining

# Version 2.0.0
- Updated the mod to Forge 2847
- Added a per-drill head energy multiplier
- Added a (toggleable) drill smart mode to make the drill only use energy when it can actually mine ores
- Added the ability to create indestructible drills by setting the durability to -1
- Added a creative drill head that is indestructible, fast, and uses no power to the default drills config
- Added a tooltip to the drill heads that shows durability, max durability, mining speed, power usage multiplier, and mining tier values
- Added a config option to configure the blocks the drills must be placed on and the blocks the pump must be placed on separately
- Added a drill rotation animation speed multiplier value to the config