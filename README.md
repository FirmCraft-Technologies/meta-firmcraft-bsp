# meta-firmcraft-bsp

The `meta-firmcraft-bsp` layer provides hardware-specific customizations, branding, and value-added features for the FirmCraft Yocto-based Linux platform targeting Raspberry Pi 4 and BeagleBone Black boards.

This layer is designed to be included in the [yocto-firmcraft-platform](https://github.com/FirmCraft-Technologies/yocto-firmcraft-platform/tree/scarthgap) and adds several enhancements on top of the base image.

---

## Layer Summary

- Custom splash screen (psplash)
- Custom wallpaper and branding
- Branded `/etc/os-release`
- SSH auto-login support
- Update hooks (OTA-ready)
- Custom Yocto image: `firmcraft-image`

---

## Directory Structure

```text
├── classes
│   └── firmcraft-banner.bbclass
├── conf
│   ├── layer.conf
│   └── machine
│       ├── beagleblack.conf
│       └── rpi4.conf
├── LICENSE
├── README.md
├── recipes-connectivity
│   └── ssh-autologin
│       └── ssh-autologin.bb
├── recipes-core
│   ├── firmcraft-ota
│   │   ├── files
│   │   │   ├── apply_update.sh
│   │   │   └── check_update.sh
│   │   └── firmcraft-ota.bb
│   └── images
│       └── firmcraft-image.bb
└── recipes-graphics
    └── firmcraft-wallpaper
        ├── files
        │   └── firmcraft-wallpaper.png
        └── firmcraft-wallpaper.bb
```
Dependencies
This layer depends on the following layers (already included in the [yocto-firmcraft-platform manifest](https://github.com/FirmCraft-Technologies/yocto-firmcraft-platform/blob/scarthgap/default.xml)):
```
meta-openembedded

meta-raspberrypi

meta-arm-toolchain

meta-arm

meta-ti-bsp

meta-beagle

meta-ti

meta-qt6
```

How to Include in Your Build
From your Yocto build directory (after sourcing the environment):

```bash
bitbake-layers add-layer ../sources/meta-firmcraft-bsp
```
Building the Image
Use the custom image recipe firmcraft-image:

```bash
MACHINE=rpi4 bitbake firmcraft-image
```
Output image will be generated at:

bash
<build-dir>/tmp/deploy/images/rpi4/firmcraft-image-rpi4.wic.bz2
Flashing the Image
Use bmaptool or dd to write the image to an SD card:

```bash
bmaptool copy \
  tmp/deploy/images/rpi4/firmcraft-image-rpi4.wic.bz2 \
  /dev/sdX
Or using dd (not recommended for large images):
```
```bash
bzcat tmp/deploy/images/rpi4/firmcraft-image-rpi4.wic.bz2 | sudo dd of=/dev/sdX bs=4M status=progress
sync
```
Replace ```/dev/sdX``` with your actual SD card device.

License
This layer is distributed under the **MIT License**. See **LICENSE** for details.

## Contact
#### FirmCraft Technologies
📧 **info@firmcraft.in**
🌐 **https://www.firmcraft.in**


