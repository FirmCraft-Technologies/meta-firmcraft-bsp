FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Add custom kernel configuration fragment for BeagleBone Black advanced features
SRC_URI:append:beaglebone = " file://bbb-advanced.cfg"

# Enable kernel config fragments
KERNEL_FEATURES:append:beaglebone = " cfg/bbb-advanced.cfg"
