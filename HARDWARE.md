# BeagleBone Black Hardware Guide

This document provides detailed information about the hardware features and configurations available in the FirmCraft BeagleBone Black BSP.

---

## 📋 Table of Contents

1. [Hardware Overview](#hardware-overview)
2. [Pin Configuration](#pin-configuration)
3. [Device Tree Overlays](#device-tree-overlays)
4. [Peripheral Configuration](#peripheral-configuration)
5. [PRU (Programmable Real-Time Unit)](#pru-programmable-real-time-unit)
6. [Power Management](#power-management)
7. [Hardware Monitoring](#hardware-monitoring)

---

## 🔧 Hardware Overview

### BeagleBone Black Specifications

- **Processor**: TI AM3358 Sitara ARM Cortex-A8 @ 1GHz
- **RAM**: 512MB DDR3
- **Flash**: 4GB eMMC
- **GPU**: SGX530 3D Graphics Accelerator
- **PRU**: 2x Programmable Real-Time Units @ 200MHz
- **Connectivity**: 10/100 Ethernet, USB Host, USB Client
- **Expansion**: 2x 46-pin headers (P8 & P9)

### Supported Features in This BSP

- ✅ All GPIO pins (65 GPIO pins available)
- ✅ I2C buses (I2C0, I2C1, I2C2)
- ✅ SPI buses (SPI0, SPI1)
- ✅ UART serial ports (UART0-5)
- ✅ PWM outputs (EHRPWM0A/B, EHRPWM1A/B, EHRPWM2A/B, ECAP0/1/2)
- ✅ ADC inputs (7 analog inputs)
- ✅ CAN bus
- ✅ PRU support
- ✅ USB OTG (gadget mode)
- ✅ MMC/SD card
- ✅ HDMI output (via cape)

---

## 📍 Pin Configuration

### P9 Header Pinout (Important Pins)

| Pin   | Function    | Overlay Required | Notes                    |
|-------|-------------|------------------|--------------------------|
| P9.1  | GND         | No               | Ground                   |
| P9.3  | 3.3V        | No               | 3.3V Power               |
| P9.5  | VDD_5V      | No               | 5V Power                 |
| P9.11 | GPIO0_30    | BB-GPIO-HELPER   | UART4_RXD                |
| P9.12 | GPIO1_28    | BB-GPIO-HELPER   | -                        |
| P9.13 | GPIO0_31    | BB-GPIO-HELPER   | UART4_TXD                |
| P9.14 | GPIO1_18    | BB-PWM-01        | EHRPWM1A                 |
| P9.15 | GPIO1_16    | BB-GPIO-HELPER   | -                        |
| P9.16 | GPIO1_19    | BB-PWM-01        | EHRPWM1B                 |
| P9.17 | GPIO0_5     | BB-I2C1/BB-SPI0  | I2C1_SCL or SPI0_CS0     |
| P9.18 | GPIO0_4     | BB-I2C1/BB-SPI0  | I2C1_SDA or SPI0_D1      |
| P9.19 | I2C2_SCL    | No               | Cape EEPROM              |
| P9.20 | I2C2_SDA    | No               | Cape EEPROM              |
| P9.21 | GPIO0_3     | BB-SPI0          | SPI0_D0                  |
| P9.22 | GPIO0_2     | BB-SPI0          | SPI0_SCLK                |
| P9.23 | GPIO1_17    | BB-GPIO-HELPER   | -                        |
| P9.24 | GPIO0_15    | BB-GPIO-HELPER   | UART1_TXD                |
| P9.26 | GPIO0_14    | BB-GPIO-HELPER   | UART1_RXD                |

### P8 Header Pinout (Selected Pins)

| Pin   | Function    | Overlay Required | Notes                    |
|-------|-------------|------------------|--------------------------|
| P8.7  | GPIO2_2     | Optional         | TIMER4                   |
| P8.8  | GPIO2_3     | Optional         | TIMER7                   |
| P8.9  | GPIO2_5     | Optional         | TIMER5                   |
| P8.10 | GPIO2_4     | Optional         | TIMER6                   |
| P8.11 | GPIO1_13    | Optional         | -                        |
| P8.12 | GPIO1_12    | Optional         | -                        |
| P8.13 | GPIO0_23    | Optional         | EHRPWM2B                 |
| P8.14 | GPIO0_26    | Optional         | -                        |
| P8.15 | GPIO1_15    | Optional         | -                        |
| P8.16 | GPIO1_14    | Optional         | -                        |
| P8.19 | GPIO0_22    | Optional         | EHRPWM2A                 |

---

## 🎛️ Device Tree Overlays

### Available Overlays

#### 1. BB-GPIO-HELPER-00A0.dtbo

**Purpose**: Exports commonly used GPIO pins for easy userspace access.

**Pins Exported**:
- P9.11, P9.12, P9.13, P9.14, P9.15, P9.16
- P9.23, P9.24, P9.26, P9.27, P9.30
- P9.41, P9.42

**Usage**:
```bash
# Overlay is automatically loaded at boot
# Access via /sys/class/gpio/
```

#### 2. BB-I2C1-00A0.dtbo

**Purpose**: Enables I2C1 bus for communication with I2C devices.

**Pins Used**:
- P9.17: I2C1_SCL (Clock)
- P9.18: I2C1_SDA (Data)

**Clock Frequency**: 400kHz (Fast Mode)

**Usage**:
```bash
# Scan for I2C devices
i2cdetect -y -r 1

# Read from I2C device at address 0x48
i2cget -y 1 0x48 0x00

# Write to I2C device
i2cset -y 1 0x48 0x00 0xFF
```

**Common I2C Devices**:
- Temperature sensors (LM75, TMP102)
- Real-time clocks (DS1307, DS3231)
- EEPROM (24LC256)
- ADC/DAC chips
- Display drivers (OLED, LCD)

#### 3. BB-SPI0-00A0.dtbo

**Purpose**: Enables SPI0 bus for high-speed serial communication.

**Pins Used**:
- P9.17: SPI0_CS0 (Chip Select)
- P9.18: SPI0_D1 (MOSI - Master Out Slave In)
- P9.21: SPI0_D0 (MISO - Master In Slave Out)
- P9.22: SPI0_SCLK (Clock)

**Max Frequency**: 16MHz

**Usage**:
```bash
# SPI device appears as /dev/spidev0.0

# Test with spi-tools
spi-config -d /dev/spidev0.0 -q
spi-pipe -d /dev/spidev0.0 -s 1000000 -b 8
```

**Common SPI Devices**:
- Flash memory (W25Q32)
- ADC/DAC (MCP3008, MCP4922)
- Display modules (ST7735, ILI9341)
- Wireless modules (nRF24L01)
- SD cards

#### 4. BB-PWM-01-00A0.dtbo

**Purpose**: Enables PWM outputs for motor control, LED dimming, etc.

**Pins Used**:
- P9.14: EHRPWM1A
- P9.16: EHRPWM1B

**Usage**:
```bash
# PWM appears in /sys/class/pwm/

# Enable PWM
echo 0 > /sys/class/pwm/pwmchip0/export

# Set period (50Hz = 20ms = 20000000ns)
echo 20000000 > /sys/class/pwm/pwmchip0/pwm0/period

# Set duty cycle (1.5ms = 1500000ns for servo center)
echo 1500000 > /sys/class/pwm/pwmchip0/pwm0/duty_cycle

# Enable output
echo 1 > /sys/class/pwm/pwmchip0/pwm0/enable
```

**Applications**:
- Servo motor control
- LED brightness control
- Motor speed control (with H-bridge)
- Audio tone generation
- Fan speed control

### Loading Overlays Dynamically

#### At Boot Time (U-Boot)

Edit `/boot/uEnv.txt`:
```bash
uboot_overlay_addr0=/lib/firmware/BB-GPIO-HELPER-00A0.dtbo
uboot_overlay_addr1=/lib/firmware/BB-I2C1-00A0.dtbo
uboot_overlay_addr2=/lib/firmware/BB-SPI0-00A0.dtbo
uboot_overlay_addr3=/lib/firmware/BB-PWM-01-00A0.dtbo
```

#### At Runtime (cape manager)

```bash
# Check available slots
cat /sys/devices/platform/bone_capemgr/slots

# Load overlay
echo BB-I2C1 > /sys/devices/platform/bone_capemgr/slots

# Unload overlay
echo -4 > /sys/devices/platform/bone_capemgr/slots  # where 4 is the slot number
```

---

## 🔌 Peripheral Configuration

### UART (Serial Ports)

Available UARTs:
- **UART0**: Debug console (P9.21, P9.22) - 115200 baud
- **UART1**: P9.24 (TX), P9.26 (RX)
- **UART2**: P9.21 (TX), P9.22 (RX)
- **UART4**: P9.11 (RX), P9.13 (TX)

**Usage**:
```bash
# Access via /dev/ttyS*
echo "Hello" > /dev/ttyS1

# Using minicom
minicom -D /dev/ttyS1 -b 9600

# Using picocom
picocom -b 115200 /dev/ttyS1
```

### ADC (Analog to Digital Converter)

- **7 analog inputs**: AIN0-AIN6
- **Resolution**: 12-bit (0-4095)
- **Voltage Range**: 0-1.8V
- **Pins**: P9.33-P9.40

**Usage**:
```bash
# Read ADC value
cat /sys/bus/iio/devices/iio:device0/in_voltage0_raw

# Convert to voltage (1.8V full scale)
# Voltage = (raw_value / 4095) * 1.8
```

### CAN Bus

- **CAN0**: Available on expansion header
- **CAN1**: Available on expansion header

**Usage**:
```bash
# Bring up CAN interface
ip link set can0 type can bitrate 500000
ip link set can0 up

# Send CAN message
cansend can0 123#DEADBEEF

# Receive CAN messages
candump can0

# Monitor CAN traffic
cangen can0 -v
```

---

## ⚡ PRU (Programmable Real-Time Unit)

### Overview

The AM3358 includes two PRUs (PRU0 and PRU1) that can run independently at 200MHz with deterministic, real-time performance.

### Features

- Independent 32-bit RISC processors
- Direct access to I/O pins (sub-10ns GPIO toggle)
- Shared memory for communication with ARM core
- Ideal for time-critical tasks

### PRU Helper Tool

```bash
# Check PRU status
pru-helper.sh status

# Load firmware to PRU0
pru-helper.sh load 0 am335x-pru0-fw

# Stop PRU1
pru-helper.sh stop 1
```

### Use Cases

- Industrial I/O control
- Real-time motor control
- Custom communication protocols
- Fast data acquisition
- LED strip control (WS2812, etc.)

---

## 🔋 Power Management

### CPU Frequency Scaling

The BSP includes CPU frequency governor support:

**Available Governors**:
- `performance`: Always run at maximum frequency
- `powersave`: Always run at minimum frequency
- `ondemand`: Dynamically adjust based on load (default)
- `userspace`: Manual frequency control

**Usage**:
```bash
# Check current governor
cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor

# Set governor
echo performance > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor

# Check available frequencies
cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies

# Set specific frequency
echo 720000 > /sys/devices/system/cpu/cpu0/cpufreq/scaling_setspeed
```

### Thermal Management

The BSP includes thermal monitoring and protection:

```bash
# Check temperature
cat /sys/class/thermal/thermal_zone0/temp

# Temperature in millidegrees Celsius
# Example: 45000 = 45°C
```

**Thermal Thresholds**:
- Normal operation: 0-85°C
- Warning threshold: 80°C
- Critical threshold: 95°C

---

## 📊 Hardware Monitoring

### Hardware Monitor Service

The BSP includes a hardware monitoring service that logs temperature and CPU frequency:

```bash
# Start monitoring service
systemctl start bbb-hardware-monitor

# Check status
systemctl status bbb-hardware-monitor

# View logs
journalctl -u bbb-hardware-monitor

# Manual status check
bbb-hardware-monitor.sh status
```

### LED Status Indicators

BeagleBone Black has 4 user-controllable LEDs (USR0-USR3):

**Default Configuration**:
- USR0: Heartbeat (system alive indicator)
- USR1: MMC0 activity (eMMC access)
- USR2: CPU activity
- USR3: MMC1 activity (SD card access)

**LED Control**:
```bash
# Initialize LEDs
bbb-led-control.sh init

# Control individual LED
bbb-led-control.sh on 0      # Turn on USR0
bbb-led-control.sh off 1     # Turn off USR1
bbb-led-control.sh blink 2   # Make USR2 blink
bbb-led-control.sh heartbeat 3  # Set USR3 to heartbeat

# Flash all LEDs
bbb-led-control.sh flash
```

---

## 🛠️ Troubleshooting

### Common Issues

#### 1. GPIO Access Denied

```bash
# Add user to gpio group
usermod -a -G gpio <username>
```

#### 2. I2C Device Not Found

```bash
# Check if I2C bus is enabled
ls -l /dev/i2c-*

# Load I2C overlay
echo BB-I2C1 > /sys/devices/platform/bone_capemgr/slots
```

#### 3. SPI Not Working

```bash
# Verify SPI device
ls -l /dev/spidev*

# Check kernel modules
lsmod | grep spi
```

#### 4. PRU Firmware Not Loading

```bash
# Check remoteproc status
ls -l /sys/class/remoteproc/

# View PRU state
cat /sys/class/remoteproc/remoteproc*/state
```

---

## 📚 References

- [BeagleBone Black System Reference Manual](https://github.com/beagleboard/beaglebone-black/wiki/System-Reference-Manual)
- [AM335x Technical Reference Manual](https://www.ti.com/lit/ug/spruh73q/spruh73q.pdf)
- [PRU Assembly Reference](http://www.ti.com/lit/ug/spruhv7b/spruhv7b.pdf)
- [Device Tree Overlay Guide](https://www.kernel.org/doc/Documentation/devicetree/overlay-notes.txt)

---

**Last Updated**: 2026-02-19  
**BSP Version**: 2.0
