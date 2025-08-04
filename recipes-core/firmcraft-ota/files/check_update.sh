#!/bin/sh
# Firmcraft BSP OTA Update Checker

UPDATE_DIR="/opt/firmcraft/ota"
UPDATE_FILE="$UPDATE_DIR/update.img"

echo "[FIRMCRAFT-OTA] Checking for update..."

if [ -f "$UPDATE_FILE" ]; then
    echo "[FIRMCRAFT-OTA] Update found at $UPDATE_FILE"
    exit 0
else
    echo "[FIRMCRAFT-OTA] No update found"
    exit 1
fi

