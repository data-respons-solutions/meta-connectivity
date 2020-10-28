DESCRIPTION = "Modem manager configuration"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += "\
	file://78-mm-quectel-ec21.rules \
	file://78-mm-quectel-ec25.rules \
	file://78-mm-quectel-uc20.rules \
	file://78-mm-quectel-em12.rules \
"

do_install_append () {
	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/78-mm-quectel-ec21.rules ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/78-mm-quectel-ec25.rules ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/78-mm-quectel-uc20.rules ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/78-mm-quectel-em12.rules ${D}${sysconfdir}/udev/rules.d
}

FILES_${PN} += " \
    ${sysconfdir}/udev/rules.d \
"
