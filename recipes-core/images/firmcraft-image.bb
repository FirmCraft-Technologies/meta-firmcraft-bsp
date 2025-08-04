DESCRIPTION = "FirmCraft custom Yocto image with GUI"
LICENSE = "MIT"
inherit core-image
IMAGE_INSTALL += "networkmanager firmcraft-wallpaper ssh-autologin"
IMAGE_FEATURES += "ssh-server-openssh"
SPLASH = "psplash-firmcraft"

inherit firmcraft-banner

IMAGE_PREPROCESS_COMMAND += "firmcraft_os_release;"
firmcraft_os_release() {
    echo 'NAME="Firmcraft Embedded Linux"' > ${IMAGE_ROOTFS}/etc/os-release
    echo 'VERSION="1.0 (Scarthgap)"' >> ${IMAGE_ROOTFS}/etc/os-release
}