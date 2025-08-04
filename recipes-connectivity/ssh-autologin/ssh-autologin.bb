SUMMARY = "Enable SSH autologin"
LICENSE = "MIT"
inherit systemd
do_install() {
  install -d ${D}${sysconfdir}/systemd/system/getty@tty1.service.d
  cat <<EOF > ${D}${sysconfdir}/systemd/system/getty@tty1.service.d/autologin.conf
[Service]
ExecStart=
ExecStart=-/sbin/agetty --autologin root --noclear %I $TERM
EOF
}
FILES:${PN} += "${sysconfdir}/systemd"