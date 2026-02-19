# meta-firmcraft-bsp

The `meta-firmcraft-bsp` layer provides **advanced hardware-specific customizations**, branding, and professional BSP features for the FirmCraft Yocto-based Linux platform targeting **Raspberry Pi 4** and **BeagleBone Black** boards.

This layer is designed to be included in the [yocto-firmcraft-platform](https://github.com/FirmCraft-Technologies/yocto-firmcraft-platform/tree/scarthgap) and showcases enterprise-grade BSP development expertise.

---

## 🚀 Features Overview

### Common Features (All Platforms)
- ✅ Custom splash screen (psplash)
- ✅ Custom wallpaper and branding
- ✅ Branded `/etc/os-release`
- ✅ SSH auto-login support
- ✅ OTA update framework
- ✅ Custom Yocto image: `firmcraft-image`

### 🔧 BeagleBone Black Advanced Features

#### Hardware Support
- **Advanced Kernel Configuration**: Real-time features, Industrial I/O, CAN bus support
- **Device Tree Overlays**: GPIO, I2C, SPI, PWM overlays for easy peripheral configuration
- **PRU Support**: Programmable Real-Time Unit tools and helpers
- **Cape Manager**: Dynamic hardware configuration support

#### System Services
- **Hardware Monitoring**: Temperature and CPU frequency monitoring service
- **LED Control**: Comprehensive LED management for the 4 user LEDs
- **Boot-time Configuration**: Automatic hardware initialization

#### Development Tools
- **Package Group**: Comprehensive toolset including:
  - I2C tools (`i2c-tools`)
  - SPI tools (`spi-tools`)
  - GPIO utilities (`libgpiod`, `devmem2`)
  - CAN utilities (`can-utils`)
  - Network debugging (`tcpdump`, `iperf3`, `ethtool`)
  - Development tools (`gdb`, `strace`, `htop`)
  - Python with hardware libraries
  
#### Enhanced Machine Configuration
- Optimized CPU tuning for Cortex-A8
- SGX530 GPU support
- USB gadget support (Ethernet, Mass Storage)
- Thermal management
- Watchdog support
- Real-time capabilities

---

## 📁 Directory Structure

```text
meta-firmcraft-bsp/
├── classes/
│   └── firmcraft-banner.bbclass           # Build banner customization
├── conf/
│   ├── layer.conf                         # Layer configuration
│   └── machine/
│       ├── beagleblack.conf              # Advanced BBB machine config
│       └── rpi4.conf                      # RPi4 machine config
├── recipes-bsp/
│   ├── device-tree/                       # Device tree overlays
│   │   ├── files/
│   │   │   ├── BB-GPIO-HELPER-00A0.dts   # GPIO helper overlay
│   │   │   ├── BB-I2C1-00A0.dts          # I2C1 overlay
│   │   │   ├── BB-SPI0-00A0.dts          # SPI0 overlay
│   │   │   └── BB-PWM-01-00A0.dts        # PWM overlay
│   │   └── bbb-device-tree-overlays_1.0.bb
│   ├── bbb-hardware/                      # Hardware tools
│   │   ├── files/
│   │   │   ├── bbb-hardware-monitor.sh
│   │   │   ├── bbb-hardware-monitor.service
│   │   │   ├── bbb-led-control.sh
│   │   │   └── bbb-led-control.service
│   │   └── bbb-hardware-tools_1.0.bb
│   ├── pru-support/                       # PRU tools
│   │   ├── files/
│   │   │   └── pru-helper.sh
│   │   └── pru-support-tools_1.0.bb
│   └── u-boot/
│       ├── files/
│       │   └── bbb-uboot-env.txt
│       └── u-boot-ti-staging_%.bbappend
├── recipes-connectivity/
│   └── ssh-autologin/
│       └── ssh-autologin.bb
├── recipes-core/
│   ├── firmcraft-ota/
│   │   ├── files/
│   │   │   ├── apply_update.sh
│   │   │   └── check_update.sh
│   │   └── firmcraft-ota.bb
│   ├── images/
│   │   └── firmcraft-image.bb            # Enhanced image recipe
│   └── packagegroups/
│       └── packagegroup-bbb-tools.bb     # Development tools package
├── recipes-graphics/
│   └── firmcraft-wallpaper/
│       ├── files/
│       │   └── firmcraft-wallpaper.png
│       └── firmcraft-wallpaper.bb
├── recipes-kernel/
│   └── linux/
│       ├── files/
│       │   └── bbb-advanced.cfg          # Kernel config fragment
│       └── linux-ti-staging_%.bbappend
├── LICENSE
└── README.md
```

---

## 🔗 Dependencies

This layer depends on the following layers (already included in the [yocto-firmcraft-platform manifest](https://github.com/FirmCraft-Technologies/yocto-firmcraft-platform/blob/scarthgap/default.xml)):

- `meta-openembedded`
- `meta-raspberrypi`
- `meta-arm-toolchain`
- `meta-arm`
- `meta-ti-bsp`
- `meta-beagle`
- `meta-ti`
- `meta-qt6`

---

## 🛠️ How to Include in Your Build

From your Yocto build directory (after sourcing the environment):

```bash
bitbake-layers add-layer ../sources/meta-firmcraft-bsp
```

---

## 🏗️ Building the Image

### For BeagleBone Black (Recommended)

```bash
MACHINE=beaglebone-yocto bitbake firmcraft-image
```

### For Raspberry Pi 4

```bash
MACHINE=rpi4 bitbake firmcraft-image
```

### Output

The image will be generated at:

**BeagleBone Black:**
```
<build-dir>/tmp/deploy/images/beaglebone-yocto/firmcraft-image-beaglebone-yocto.wic.xz
```

**Raspberry Pi 4:**
```
<build-dir>/tmp/deploy/images/rpi4/firmcraft-image-rpi4.wic.bz2
```

---

## 💾 Flashing the Image

### Using bmaptool (Recommended)

```bash
bmaptool copy \
  tmp/deploy/images/beaglebone-yocto/firmcraft-image-beaglebone-yocto.wic.xz \
  /dev/sdX
```

### Using dd

```bash
xzcat tmp/deploy/images/beaglebone-yocto/firmcraft-image-beaglebone-yocto.wic.xz | \
  sudo dd of=/dev/sdX bs=4M status=progress conv=fsync
sync
```

**⚠️ Replace `/dev/sdX` with your actual SD card device.**

---

## 🔧 BeagleBone Black Hardware Configuration

### Device Tree Overlays

The following device tree overlays are included and can be loaded at boot:

#### GPIO Helper (`BB-GPIO-HELPER-00A0.dtbo`)
Exports commonly used GPIO pins for userspace access.

#### I2C1 (`BB-I2C1-00A0.dtbo`)
Enables I2C1 bus on pins:
- **P9.17**: SCL (Clock)
- **P9.18**: SDA (Data)

#### SPI0 (`BB-SPI0-00A0.dtbo`)
Enables SPI0 bus on pins:
- **P9.17**: CS0 (Chip Select)
- **P9.18**: MOSI (Master Out Slave In)
- **P9.21**: MISO (Master In Slave Out)
- **P9.22**: SCLK (Clock)

#### PWM (`BB-PWM-01-00A0.dtbo`)
Enables PWM output on:
- **P9.14**: EHRPWM1A
- **P9.16**: EHRPWM1B

### Loading Overlays

Overlays are installed to `/lib/firmware/` and can be loaded via U-Boot or at runtime using the cape manager.

---

## 🖥️ System Services

### Hardware Monitor

Monitors temperature and CPU frequency:

```bash
# Start service
systemctl start bbb-hardware-monitor

# Check status
systemctl status bbb-hardware-monitor

# View logs
journalctl -u bbb-hardware-monitor

# Manual status check
bbb-hardware-monitor.sh status
```

### LED Control

Control the 4 user LEDs on BeagleBone Black:

```bash
# Initialize LEDs with default patterns
bbb-led-control.sh init

# Turn on LED 0
bbb-led-control.sh on 0

# Turn off LED 1
bbb-led-control.sh off 1

# Set LED 2 to heartbeat
bbb-led-control.sh heartbeat 2

# Make LED 3 blink
bbb-led-control.sh blink 3

# Flash all LEDs
bbb-led-control.sh flash
```

---

## 🔌 PRU (Programmable Real-Time Unit) Support

The layer includes tools for managing the PRU subsystem:

```bash
# Check PRU status
pru-helper.sh status

# Load firmware to PRU 0
pru-helper.sh load 0 am335x-pru0-fw

# Stop PRU 1
pru-helper.sh stop 1
```

---

## 🧰 Development Tools

The image includes a comprehensive set of development tools via `packagegroup-bbb-tools`:

### Hardware Access Tools
- **I2C**: `i2c-tools` - I2C bus scanning and device interaction
- **SPI**: `spi-tools` - SPI device testing
- **GPIO**: `libgpiod`, `libgpiod-tools`, `devmem2`
- **CAN**: `can-utils` - CAN bus utilities

### Network Tools
- `tcpdump` - Network packet analyzer
- `iperf3` - Network performance testing
- `ethtool` - Ethernet device configuration
- `net-tools`, `iproute2` - Network configuration

### Debug & Development
- `gdb`, `gdbserver` - GNU Debugger
- `strace` - System call tracer
- `htop` - Interactive process viewer
- `vim`, `nano` - Text editors
- `screen`, `minicom`, `picocom` - Serial communication

### Python Support
- `python3` with `pip`
- `python3-smbus` - I2C/SMBus support
- `python3-serial` - Serial port access

---

## 🏗️ Advanced Kernel Features

The custom kernel configuration (`bbb-advanced.cfg`) enables:

### Real-Time Features
- Preemptible kernel
- High-resolution timers

### Industrial I/O
- ADC support (TI AM335x ADC)
- Industrial I/O buffers and triggers

### Communication Protocols
- CAN bus (Controller Area Network)
- I2C master and slave mode
- SPI master mode
- Multiple UART support

### PRU Features
- PRU remoteproc support
- RPMSG communication

### Power Management
- CPU frequency scaling
- Dynamic voltage and frequency scaling (DVFS)
- Thermal management

### Security
- Security framework
- Network security

---

## 📊 BSP Expertise Showcase

This meta layer demonstrates advanced BSP development skills including:

1. **Machine Configuration**: Comprehensive BeagleBone Black machine setup with optimized settings
2. **Kernel Customization**: Advanced kernel configuration fragments for industrial features
3. **Device Tree Engineering**: Custom overlays for flexible hardware configuration
4. **System Services**: Hardware monitoring and management services with systemd integration
5. **U-Boot Customization**: Boot loader environment customization
6. **Package Management**: Organized package groups for different use cases
7. **Development Environment**: Complete toolchain for embedded development
8. **Documentation**: Professional documentation and usage guides

---

## 🧪 Testing Your BSP

After booting the image on BeagleBone Black:

### 1. Verify Hardware Monitor

```bash
systemctl status bbb-hardware-monitor
bbb-hardware-monitor.sh status
```

### 2. Test LED Control

```bash
bbb-led-control.sh init
bbb-led-control.sh flash
```

### 3. Check I2C Bus

```bash
i2cdetect -l
i2cdetect -y -r 1
```

### 4. Verify GPIO Access

```bash
gpiodetect
gpioinfo
```

### 5. Check PRU Status

```bash
pru-helper.sh status
```

### 6. Monitor System

```bash
htop
cat /sys/class/thermal/thermal_zone0/temp
cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq
```

---

## 🔒 Security Features

- Secure boot support (can be enabled)
- Watchdog timer support
- Security framework enabled in kernel
- Regular security updates via OTA

---

## 🚀 OTA Updates

The layer includes an OTA update framework:

### Check for Updates

```bash
/opt/firmcraft/ota/check_update.sh
```

### Apply Updates

```bash
/opt/firmcraft/ota/apply_update.sh
```

Place the update image at `/opt/firmcraft/ota/update.img` for automatic deployment.

---

## 📝 License

This layer is distributed under the **MIT License**. See [LICENSE](LICENSE) for details.

---

## 📧 Contact

**FirmCraft Technologies**

- 📧 Email: **info@firmcraft.in**
- 🌐 Website: **https://www.firmcraft.in**
- 💼 LinkedIn: [FirmCraft Technologies](https://www.linkedin.com/company/firmcraft-technologies)

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.

---

## 📚 Additional Resources

- [Yocto Project Documentation](https://docs.yoctoproject.org/)
- [BeagleBone Black System Reference Manual](https://github.com/beagleboard/beaglebone-black/wiki/System-Reference-Manual)
- [TI AM335x Technical Reference Manual](https://www.ti.com/product/AM3358)
- [Device Tree Overlay Documentation](https://www.kernel.org/doc/Documentation/devicetree/overlay-notes.txt)

---

**Version**: 2.0  
**Yocto Release**: Scarthgap  
**Last Updated**: 2026-02-19
