DESCRIPTION = "Firmcraft OTA Hook Framework"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://check_update.sh \
           file://apply_update.sh"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/opt/firmcraft/ota
    install -m 0755 ${WORKDIR}/check_update.sh ${D}/opt/firmcraft/ota/
    install -m 0755 ${WORKDIR}/apply_update.sh ${D}/opt/firmcraft/ota/
}

FILES:${PN} = "/opt/firmcraft/ota/*"
