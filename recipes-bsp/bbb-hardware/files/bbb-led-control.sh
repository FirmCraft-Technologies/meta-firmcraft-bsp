#!/bin/sh
# BeagleBone Black LED Control Script
# Controls the 4 user LEDs on the BBB

LED_BASE="/sys/class/leds/beaglebone:green:usr"

set_led() {
    led_num=$1
    state=$2
    
    if [ -d "${LED_BASE}${led_num}" ]; then
        echo "$state" > "${LED_BASE}${led_num}/brightness"
    fi
}

set_trigger() {
    led_num=$1
    trigger=$2
    
    if [ -d "${LED_BASE}${led_num}" ]; then
        echo "$trigger" > "${LED_BASE}${led_num}/trigger"
    fi
}

case "$1" in
    init)
        # Initialize LEDs with default behavior
        set_trigger 0 heartbeat
        set_trigger 1 mmc0
        set_trigger 2 cpu0
        set_trigger 3 mmc1
        echo "BBB LEDs initialized"
        ;;
    heartbeat)
        set_trigger ${2:-0} heartbeat
        ;;
    on)
        set_trigger ${2:-0} none
        set_led ${2:-0} 1
        ;;
    off)
        set_trigger ${2:-0} none
        set_led ${2:-0} 0
        ;;
    blink)
        set_trigger ${2:-0} timer
        ;;
    flash)
        # Flash all LEDs
        for i in 0 1 2 3; do
            set_trigger $i none
            set_led $i 1
        done
        sleep 0.5
        for i in 0 1 2 3; do
            set_led $i 0
        done
        ;;
    *)
        echo "Usage: $0 {init|heartbeat|on|off|blink|flash} [led_number]"
        echo "  led_number: 0-3 (default: 0)"
        echo "Examples:"
        echo "  $0 init          - Initialize LEDs with default triggers"
        echo "  $0 heartbeat 0   - Set LED0 to heartbeat"
        echo "  $0 on 2          - Turn LED2 on"
        echo "  $0 blink 1       - Make LED1 blink"
        exit 1
        ;;
esac

exit 0
