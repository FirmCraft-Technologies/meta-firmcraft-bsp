#!/bin/sh
# PRU Firmware Loading Helper Script

PRU_FIRMWARE_DIR="/lib/firmware"
PRU_REMOTEPROC_DIR="/sys/class/remoteproc"

load_pru_firmware() {
    pru_num=$1
    firmware=$2
    
    # Find the remoteproc device for the PRU
    for rproc in ${PRU_REMOTEPROC_DIR}/remoteproc*; do
        if [ -d "$rproc" ]; then
            name=$(cat "${rproc}/name" 2>/dev/null)
            if echo "$name" | grep -q "pru.*${pru_num}"; then
                echo "Loading firmware $firmware to $name"
                
                # Stop PRU if running
                echo "stop" > "${rproc}/state" 2>/dev/null
                
                # Set firmware
                echo "$firmware" > "${rproc}/firmware"
                
                # Start PRU
                echo "start" > "${rproc}/state"
                
                echo "PRU $pru_num firmware loaded successfully"
                return 0
            fi
        fi
    done
    
    echo "Error: Could not find PRU $pru_num remoteproc device"
    return 1
}

stop_pru() {
    pru_num=$1
    
    for rproc in ${PRU_REMOTEPROC_DIR}/remoteproc*; do
        if [ -d "$rproc" ]; then
            name=$(cat "${rproc}/name" 2>/dev/null)
            if echo "$name" | grep -q "pru.*${pru_num}"; then
                echo "Stopping PRU $pru_num ($name)"
                echo "stop" > "${rproc}/state" 2>/dev/null
                return 0
            fi
        fi
    done
    
    echo "Error: Could not find PRU $pru_num remoteproc device"
    return 1
}

status_pru() {
    echo "PRU Status:"
    echo "----------------------------------------"
    
    for rproc in ${PRU_REMOTEPROC_DIR}/remoteproc*; do
        if [ -d "$rproc" ]; then
            name=$(cat "${rproc}/name" 2>/dev/null)
            if echo "$name" | grep -q "pru"; then
                state=$(cat "${rproc}/state" 2>/dev/null)
                firmware=$(cat "${rproc}/firmware" 2>/dev/null)
                echo "Device: $name"
                echo "  State: $state"
                echo "  Firmware: $firmware"
                echo ""
            fi
        fi
    done
}

case "$1" in
    load)
        if [ -z "$2" ] || [ -z "$3" ]; then
            echo "Usage: $0 load <pru_num> <firmware>"
            echo "Example: $0 load 0 am335x-pru0-fw"
            exit 1
        fi
        load_pru_firmware "$2" "$3"
        ;;
    stop)
        if [ -z "$2" ]; then
            echo "Usage: $0 stop <pru_num>"
            exit 1
        fi
        stop_pru "$2"
        ;;
    status)
        status_pru
        ;;
    *)
        echo "PRU Firmware Helper"
        echo "Usage: $0 {load|stop|status} [options]"
        echo ""
        echo "Commands:"
        echo "  load <pru_num> <firmware>  - Load firmware to PRU"
        echo "  stop <pru_num>             - Stop PRU"
        echo "  status                     - Show PRU status"
        exit 1
        ;;
esac

exit 0
