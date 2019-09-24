# procps sets reverse path filtering to "strict" mode by default.
# We disable the filter.

do_install_append() {
    for keyword in \
        net.ipv4.conf.default.rp_filter \
        net.ipv4.conf.all.rp_filter \
    ; do
        sed -i 's,'"$keyword"'=.*,'"$keyword"'=0,' ${D}${sysconfdir}/sysctl.conf
    done
}
