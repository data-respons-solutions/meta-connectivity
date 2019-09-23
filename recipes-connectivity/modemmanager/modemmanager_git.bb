SUMMARY = "ModemManager is a daemon controlling broadband devices/connections"
DESCRIPTION = "ModemManager is a DBus-activated daemon which controls mobile broadband (2G/3G/4G) devices and connections"
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/ModemManager/"
LICENSE = "GPL-2.0 & LGPL-2.1"
LIC_FILES_CHKSUM = " \
    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c \
"

inherit autotools pkgconfig systemd vala gobject-introspection bash-completion

DEPENDS = "glib-2.0 libgudev dbus-glib intltool-native libxslt-native"

SRC_URI = "git://gitlab.freedesktop.org/mobile-broadband/ModemManager.git;protocol=https;branch=mm-1-10 \
	file://78-mm-quectel-ec21.rules \
	file://78-mm-quectel-ec25.rules \
	file://78-mm-quectel-uc20.rules \
	file://0001-Set-service-dependency-on-polkit-if-used.patch \
"
SRCREV = "4fc7296b09300985be1aff7a4fca17416c819fb6"
S = "${WORKDIR}/git"

PACKAGECONFIG ??= "mbim qmi \
    ${@bb.utils.filter('DISTRO_FEATURES', 'systemd polkit', d)} \
"
#EXTRA_AUTORECONF = "--exclude=aclocal"
EXTRA_AUTORECONF = "-I ${S}"
EXTRA_OECONF = "--disable-gtk-doc --disable-nls"

PACKAGECONFIG[systemd] = "--with-systemdsystemunitdir=${systemd_unitdir}/system/,,"
PACKAGECONFIG[polkit] = "--with-polkit=yes,--with-polkit=no,polkit"
# Support WWAN modems and devices which speak the Mobile Interface Broadband Model (MBIM) protocol.
PACKAGECONFIG[mbim] = "--with-mbim,--without-mbim,libmbim"
# Support WWAN modems and devices which speak the Qualcomm MSM Interface (QMI) protocol.
PACKAGECONFIG[qmi] = "--with-qmi,--without-qmi,libqmi"

do_install_append () {
	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/78-mm-quectel-ec21.rules ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/78-mm-quectel-ec25.rules ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/78-mm-quectel-uc20.rules ${D}${sysconfdir}/udev/rules.d
}

FILES_${PN} += " \
    ${datadir}/icons \
    ${@bb.utils.contains("DISTRO_FEATURES", "polkit", "${datadir}/polkit-1 ", "", d)} \
    ${datadir}/dbus-1 \
    ${libdir}/ModemManager \
    ${systemd_unitdir}/system \
    ${sysconfdir}/udev/rules.d \
    ${datadir}/ModemManager \
"

FILES_${PN}-dev += " \
    ${libdir}/ModemManager/*.la \
"

FILES_${PN}-staticdev += " \
    ${libdir}/ModemManager/*.a \
"

FILES_${PN}-dbg += "${libdir}/ModemManager/.debug"

SYSTEMD_SERVICE_${PN} = "ModemManager.service"