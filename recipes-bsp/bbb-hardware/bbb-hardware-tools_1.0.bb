SUMMARY = "BeagleBone Black Hardware Tools"
DESCRIPTION = "Hardware monitoring and LED control utilities for BeagleBone Black"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

SRC_URI = " \
    file://bbb-hardware-monitor.sh \
    file://bbb-hardware-monitor.service \
    file://bbb-led-control.sh \
    file://bbb-led-control.service \
"

S = "${WORKDIR}"

SYSTEMD_SERVICE:${PN} = "bbb-hardware-monitor.service bbb-led-control.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install() {
    # Install scripts
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/bbb-hardware-monitor.sh ${D}${bindir}/
    install -m 0755 ${WORKDIR}/bbb-led-control.sh ${D}${bindir}/
    
    # Install systemd service files
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/bbb-hardware-monitor.service ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/bbb-led-control.service ${D}${systemd_unitdir}/system/
    
    # Create log directory
    install -d ${D}${localstatedir}/log
}

FILES:${PN} += " \
    ${bindir}/bbb-hardware-monitor.sh \
    ${bindir}/bbb-led-control.sh \
    ${systemd_unitdir}/system/bbb-hardware-monitor.service \
    ${systemd_unitdir}/system/bbb-led-control.service \
"

RDEPENDS:${PN} = "bash"

COMPATIBLE_MACHINE = "beaglebone"
