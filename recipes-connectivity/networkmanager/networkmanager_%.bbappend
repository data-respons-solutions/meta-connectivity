PACKAGECONFIG_append = " ${@bb.utils.contains('MACHINE_FEATURES', '3g', 'modemmanager', '',d)}"
PACKAGECONFIG_remove = "bluez"

RRECOMMENDS_${PN} += "networkmanager-conf"