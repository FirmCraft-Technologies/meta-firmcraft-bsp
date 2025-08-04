SUMMARY = "FirmCraft Wallpaper"
LICENSE = "MIT"
SRC_URI = "file://firmcraft-wallpaper.png"
do_install() {
  install -d ${D}${datadir}/firmcraft
  install -m 0644 ${WORKDIR}/firmcraft-wallpaper.png ${D}${datadir}/firmcraft/
}
FILES:${PN} += "${datadir}/firmcraft"