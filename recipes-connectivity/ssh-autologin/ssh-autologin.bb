SUMMARY = "Enable SSH autologin"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

S = "${WORKDIR}"

do_install() {
  install -d ${D}${sysconfdir}/systemd/system/getty@tty1.service.d
  cat <<EOF > ${D}${sysconfdir}/systemd/system/getty@tty1.service.d/autologin.conf
[Service]
ExecStart=
ExecStart=-/sbin/agetty --autologin root --noclear %I \$TERM
EOF
}

FILES:${PN} += "${sysconfdir}/systemd"