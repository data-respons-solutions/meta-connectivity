DESCRIPTION = "Network manager configuration"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI_append = " \
	file://telenor.nmconnection \
"

do_install_append () {
	install -d ${D}${sysconfdir}/NetworkManager/system-connections
	install -m 0600 ${WORKDIR}/telenor.nmconnection ${D}${sysconfdir}/NetworkManager/system-connections
}

FILES_${PN} += "${sysconfdir}/NetworkManager/system-connections"