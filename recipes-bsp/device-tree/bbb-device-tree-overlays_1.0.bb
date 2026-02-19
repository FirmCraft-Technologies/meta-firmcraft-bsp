SUMMARY = "BeagleBone Black Device Tree Overlays"
DESCRIPTION = "Custom device tree overlays for BeagleBone Black GPIO, I2C, SPI, and PWM"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit devicetree

COMPATIBLE_MACHINE = "beaglebone"

SRC_URI = " \
    file://BB-GPIO-HELPER-00A0.dts \
    file://BB-I2C1-00A0.dts \
    file://BB-SPI0-00A0.dts \
    file://BB-PWM-01-00A0.dts \
"

S = "${WORKDIR}"

do_compile() {
    for dts_file in ${WORKDIR}/*.dts; do
        if [ -f "$dts_file" ]; then
            dtc_basename=$(basename "$dts_file" .dts)
            bbplain "Compiling device tree overlay: $dtc_basename"
            dtc -@ -I dts -O dtb -o ${B}/${dtc_basename}.dtbo "$dts_file"
            if [ $? -ne 0 ]; then
                bbfatal "Failed to compile device tree overlay: $dtc_basename"
            fi
        fi
    done
}

do_install() {
    install -d ${D}/lib/firmware
    for dtbo_file in ${B}/*.dtbo; do
        if [ -f "$dtbo_file" ]; then
            install -m 0644 "$dtbo_file" ${D}/lib/firmware/
        fi
    done
}

FILES:${PN} = "/lib/firmware/*.dtbo"

PACKAGE_ARCH = "${MACHINE_ARCH}"
