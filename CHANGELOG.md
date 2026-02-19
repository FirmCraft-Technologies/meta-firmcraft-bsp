# Changelog - meta-firmcraft-bsp

All notable changes to this BSP layer are documented in this file.

## [2.0.0] - 2026-02-19

### Added - BeagleBone Black Advanced BSP Features

#### Machine Configuration
- Enhanced machine configuration with comprehensive settings for BBB
- Optimized CPU tuning for Cortex-A8
- SGX530 GPU provider configuration
- PRU (Programmable Real-Time Unit) support
- USB gadget mode support (Ethernet, Mass Storage)
- Serial console configuration
- WKS file support for disk image creation
- Multiple device tree support
- Enhanced boot loader configuration

#### Kernel Features
- Custom kernel configuration fragment with 150+ advanced options
- Real-time features (PREEMPT kernel, high-resolution timers)
- Industrial I/O subsystem support
- CAN bus support (SocketCAN)
- I2C master/slave mode support
- SPI master mode support
- GPIO and PWM support
- PRU remoteproc and RPMSG communication
- USB gadget support
- Audio support (ALSA, SoC)
- DMA engine support
- Watchdog and RTC support
- Crypto hardware acceleration
- Thermal management
- CPU frequency scaling
- Power management features
- Advanced networking (bridge, VLAN)
- LED class support
- Filesystem features (EXT4, OverlayFS)
- Security framework
- Debugging features

#### Device Tree Overlays
- **BB-GPIO-HELPER-00A0.dtbo**: GPIO helper overlay for 13 commonly used pins
- **BB-I2C1-00A0.dtbo**: I2C1 bus enablement (400kHz Fast Mode)
- **BB-SPI0-00A0.dtbo**: SPI0 bus for high-speed communication (16MHz)
- **BB-PWM-01-00A0.dtbo**: EHRPWM1 A/B channel support
- Device tree overlay recipe with proper error handling
- Overlays installed to /lib/firmware for easy loading

#### System Services
- **bbb-hardware-monitor**: Hardware monitoring service
  - Temperature monitoring
  - CPU frequency monitoring
  - Logging to /var/log/bbb-hardware.log
  - Systemd integration with auto-start
- **bbb-led-control**: LED control service
  - Management of 4 user LEDs (USR0-USR3)
  - Multiple trigger modes (heartbeat, mmc, cpu, timer, etc.)
  - Systemd integration
  - Command-line interface for LED control

#### PRU Support
- PRU firmware loading helper script
- PRU status monitoring
- Support for PRU0 and PRU1
- Remoteproc integration
- Documentation for PRU development

#### U-Boot Customization
- Custom U-Boot environment template
- FirmCraft branding in boot messages
- Device tree overlay configuration
- Network boot parameters
- Optimized boot arguments

#### Development Tools (packagegroup-bbb-tools)
- **Hardware Access**: i2c-tools, spi-tools, devmem2, libgpiod, libgpiod-tools
- **CAN Bus**: can-utils
- **Network**: tcpdump, iperf3, ethtool, net-tools, iproute2
- **Debug**: gdb, gdbserver, strace, lsof
- **Monitoring**: htop, procps
- **Editors**: vim, nano
- **Serial**: screen, minicom, picocom
- **Python**: python3, python3-pip, python3-smbus, python3-serial
- **Utilities**: git, wget, curl, rsync, tree, file, binutils
- **System**: evtest, mtd-utils, usbutils, pciutils

#### Image Recipe Enhancements
- BBB-specific package inclusion with machine conditionals
- Enhanced image features (debug-tweaks, tools-debug, package-management)
- Updated OS release information (version 2.0)
- Increased rootfs extra space (512MB)
- Support for read-only rootfs (commented for development)
- Conditional package installation based on machine type

#### Documentation
- **README.md**: Comprehensive user guide
  - Feature overview
  - Build instructions
  - Flashing guide
  - Hardware configuration guide
  - Service management
  - PRU support
  - Development tools reference
  - Testing procedures
  - Troubleshooting
- **HARDWARE.md**: Detailed hardware reference
  - BeagleBone Black specifications
  - Pin configuration tables (P8 and P9 headers)
  - Device tree overlay details
  - Peripheral configuration guides (UART, ADC, CAN)
  - PRU usage examples
  - Power management
  - Hardware monitoring
  - LED control reference
  - Troubleshooting common hardware issues
- **CHANGELOG.md**: This file

#### Layer Configuration
- Updated layer version to 2.0
- Added support for .bbappend files
- Layer priority set to 10
- Layer dependencies declared
- Compatible with Yocto Scarthgap release

#### Recipe Quality Improvements
- Added missing LIC_FILES_CHKSUM to all recipes
- Added S = "${WORKDIR}" where appropriate
- Proper FILES variables for package contents
- COMPATIBLE_MACHINE restrictions where needed
- Professional error handling
- Informative logging
- Code follows Yocto best practices

### Changed
- Updated firmcraft-image recipe with BBB-specific enhancements
- Updated layer.conf to version 2 with proper dependencies
- Enhanced ssh-autologin recipe with proper license tracking
- Improved firmcraft-ota recipe with license and FILES variable
- Enhanced firmcraft-wallpaper recipe with proper structure

### Fixed
- Recipe syntax issues
- Missing license checksums
- Proper error handling in device tree compilation
- Removed silent error suppression

## [1.0.0] - Initial Release

### Added
- Basic layer structure
- Machine configurations for RPi4 and BBB
- Custom splash screen support
- Custom wallpaper
- SSH auto-login
- OTA update framework
- Basic image recipe
- FirmCraft branding

---

## Version Numbering

This project follows [Semantic Versioning](https://semver.org/):
- MAJOR version for incompatible changes
- MINOR version for backward-compatible functionality additions
- PATCH version for backward-compatible bug fixes

---

**Maintainer**: FirmCraft Technologies  
**Contact**: info@firmcraft.in  
**Website**: https://www.firmcraft.in
