DESCRIPTION = "Firmcraft OTA Hook Framework"
LICENSE = "MIT"
SRC_URI = "file://check_update.sh \
           file://apply_update.sh"

do_install() {
    install -d ${D}/opt/firmcraft/ota
    install -m 0755 ${WORKDIR}/check_update.sh ${D}/opt/firmcraft/ota/
    install -m 0755 ${WORKDIR}/apply_update.sh ${D}/opt/firmcraft/ota/
}

