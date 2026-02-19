FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Add custom U-Boot environment
SRC_URI:append:beaglebone = " file://bbb-uboot-env.txt"

do_install:append:beaglebone() {
    # Install custom U-Boot environment template
    install -d ${D}${datadir}/u-boot
    install -m 0644 ${WORKDIR}/bbb-uboot-env.txt ${D}${datadir}/u-boot/
}

FILES:${PN}:append:beaglebone = " ${datadir}/u-boot/bbb-uboot-env.txt"
