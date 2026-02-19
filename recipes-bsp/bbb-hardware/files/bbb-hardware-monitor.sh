#!/bin/sh
### BEGIN INIT INFO
# Provides:          bbb-hardware-monitor
# Required-Start:    $remote_fs $syslog
# Required-Stop:     $remote_fs $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: BeagleBone Black Hardware Monitor
# Description:       Monitor temperature, voltage, and system health
### END INIT INFO

# BeagleBone Black Hardware Monitoring Service
# Monitors system temperature, CPU frequency, and logs hardware status

LOG_FILE="/var/log/bbb-hardware.log"
THERMAL_ZONE="/sys/class/thermal/thermal_zone0/temp"
CPU_FREQ="/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq"
INTERVAL=60  # Check every 60 seconds

log_message() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" | tee -a "$LOG_FILE"
}

get_temperature() {
    if [ -f "$THERMAL_ZONE" ]; then
        temp=$(cat "$THERMAL_ZONE")
        # Convert from millidegrees to degrees
        temp_c=$((temp / 1000))
        echo "$temp_c"
    else
        echo "N/A"
    fi
}

get_cpu_freq() {
    if [ -f "$CPU_FREQ" ]; then
        freq=$(cat "$CPU_FREQ")
        # Convert from kHz to MHz
        freq_mhz=$((freq / 1000))
        echo "$freq_mhz"
    else
        echo "N/A"
    fi
}

monitor_loop() {
    log_message "BBB Hardware Monitor started"
    
    while true; do
        temp=$(get_temperature)
        freq=$(get_cpu_freq)
        
        log_message "Status: Temp=${temp}°C, CPU Freq=${freq}MHz"
        
        # Check for high temperature
        if [ "$temp" != "N/A" ] && [ "$temp" -gt 80 ]; then
            log_message "WARNING: High temperature detected: ${temp}°C"
        fi
        
        sleep "$INTERVAL"
    done
}

case "$1" in
    start)
        log_message "Starting BBB Hardware Monitor..."
        monitor_loop &
        echo $! > /var/run/bbb-hardware-monitor.pid
        ;;
    stop)
        log_message "Stopping BBB Hardware Monitor..."
        if [ -f /var/run/bbb-hardware-monitor.pid ]; then
            kill $(cat /var/run/bbb-hardware-monitor.pid) 2>/dev/null
            rm -f /var/run/bbb-hardware-monitor.pid
        fi
        ;;
    restart)
        $0 stop
        sleep 2
        $0 start
        ;;
    status)
        temp=$(get_temperature)
        freq=$(get_cpu_freq)
        echo "Temperature: ${temp}°C"
        echo "CPU Frequency: ${freq}MHz"
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|status}"
        exit 1
        ;;
esac

exit 0
