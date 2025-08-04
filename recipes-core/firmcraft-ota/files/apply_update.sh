#!/bin/sh
# Firmcraft BSP OTA Update Applier

UPDATE_DIR="/opt/firmcraft/ota"
UPDATE_FILE="$UPDATE_DIR/update.img"
LOG_FILE="/var/log/firmcraft-ota.log"

echo "[FIRMCRAFT-OTA] Applying update..." | tee -a "$LOG_FILE"

if [ ! -f "$UPDATE_FILE" ]; then
    echo "[FIRMCRAFT-OTA] No update file found. Abort." | tee -a "$LOG_FILE"
    exit 1
fi


echo "[FIRMCRAFT-OTA] Flashing image (simulation)..." | tee -a "$LOG_FILE"
sleep 3

echo "[FIRMCRAFT-OTA] Update applied successfully. Rebooting..." | tee -a "$LOG_FILE"
rm -f "$UPDATE_FILE"

# Uncomment below for actual use
# reboot

exit 0

