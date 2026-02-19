DESCRIPTION = "FirmCraft custom Yocto image with advanced BSP features for BeagleBone Black"
LICENSE = "MIT"

inherit core-image

# Base packages
IMAGE_INSTALL += " \
    networkmanager \
    firmcraft-wallpaper \
    ssh-autologin \
    firmcraft-ota \
"

# BeagleBone Black specific packages (conditional on machine)
IMAGE_INSTALL:append:beaglebone = " \
    bbb-device-tree-overlays \
    bbb-hardware-tools \
    pru-support-tools \
    packagegroup-bbb-tools \
    kernel-modules \
    kernel-devicetree \
"

# Image features
IMAGE_FEATURES += " \
    ssh-server-openssh \
    debug-tweaks \
    tools-debug \
    package-management \
"

SPLASH = "psplash-firmcraft"

inherit firmcraft-banner

# OS Release customization
IMAGE_PREPROCESS_COMMAND += "firmcraft_os_release;"
firmcraft_os_release() {
    echo 'NAME="FirmCraft Embedded Linux"' > ${IMAGE_ROOTFS}/etc/os-release
    echo 'VERSION="2.0 (Scarthgap)"' >> ${IMAGE_ROOTFS}/etc/os-release
    echo 'ID=firmcraft' >> ${IMAGE_ROOTFS}/etc/os-release
    echo 'VERSION_ID="2.0"' >> ${IMAGE_ROOTFS}/etc/os-release
    echo 'PRETTY_NAME="FirmCraft Embedded Linux 2.0 (BeagleBone Black BSP)"' >> ${IMAGE_ROOTFS}/etc/os-release
    echo 'HOME_URL="https://www.firmcraft.in"' >> ${IMAGE_ROOTFS}/etc/os-release
    echo 'SUPPORT_URL="https://www.firmcraft.in/support"' >> ${IMAGE_ROOTFS}/etc/os-release
    echo 'BUG_REPORT_URL="https://www.firmcraft.in/bugs"' >> ${IMAGE_ROOTFS}/etc/os-release
}

# Root filesystem extra space (in KB)
IMAGE_ROOTFS_EXTRA_SPACE = "524288"

# Enable read-only rootfs for production (commented for development)
# IMAGE_FEATURES += "read-only-rootfs"