SUMMARY = "BeagleBone Black PRU Support Tools"
DESCRIPTION = "Tools and scripts for managing PRU (Programmable Real-Time Unit) on BeagleBone Black"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://pru-helper.sh \
"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/pru-helper.sh ${D}${bindir}/
}

FILES:${PN} = "${bindir}/pru-helper.sh"

RDEPENDS:${PN} = "bash"

COMPATIBLE_MACHINE = "beaglebone"
