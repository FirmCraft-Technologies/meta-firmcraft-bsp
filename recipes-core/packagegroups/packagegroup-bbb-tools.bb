SUMMARY = "BeagleBone Black Development and Debug Tools Package Group"
DESCRIPTION = "Collection of tools for BBB development, debugging, and hardware access"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit packagegroup

RDEPENDS:${PN} = " \
    i2c-tools \
    spi-tools \
    devmem2 \
    ethtool \
    iperf3 \
    can-utils \
    evtest \
    mtd-utils \
    usbutils \
    pciutils \
    lsof \
    strace \
    tcpdump \
    net-tools \
    iproute2 \
    procps \
    htop \
    nano \
    vim \
    screen \
    minicom \
    picocom \
    git \
    wget \
    curl \
    rsync \
    tree \
    file \
    binutils \
    gdb \
    gdbserver \
"

# Optional Python tools for development
RDEPENDS:${PN} += " \
    python3 \
    python3-pip \
    python3-smbus \
    python3-serial \
"

# GPIO and hardware libraries
RDEPENDS:${PN} += " \
    libgpiod \
    libgpiod-tools \
"

COMPATIBLE_MACHINE = "beaglebone"
