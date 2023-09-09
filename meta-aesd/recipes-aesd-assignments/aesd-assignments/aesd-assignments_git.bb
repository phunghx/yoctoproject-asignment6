# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "CLOSED"
#SRC_URI[sha256sum] = "04e0082cb0426effa4bd98489bccf6053941bd1ba5163697f902d026b6180792"
#LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=5823f887b06a2e9a885c2b5e0b516ff10433c42bf6d5897b7dcbd61501a1f298"

# TODO: Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
SRC_URI = "git://github.com/phunghx/assignments-3-and-later-phunghx.git;protocol=https;branch=master"

PV = "1.0+git${SRCPV}"
# TODO: set to reference a specific commit hash in your assignment repo
SRCREV = "dfd22fc9873f946fd37a611f56a91511fb6de996"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

# TODO: Add the aesdsocket application and any other files you need to install
# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${bindir}/aesdsocket"
# TODO: customize these as necessary for any libraries you need for your application
# (and remove comment)
TARGET_LDFLAGS += "-pthread -lrt"


do_configure () {
	:
}

do_compile () {
	make clean
	oe_runmake
}
pkg_postinst_ontarget() {
    #!/bin/bash
    // bash script you want to run
    sh ${D}${sysconfdir}/init.d/aesdsocket-start-stop start
}
do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
	install -d ${D}${bindir}
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdsocket ${D}${bindir}/	
	install -m 0755 ${S}/aesdsocket-start-stop ${D}${bindir}/	
    install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d/
    #ln -sf ${D}${sysconfdir}/init.d/aesdsocket-start-stop ${D}${sysconfdir}/init.d/S99aesdsocket
	#update-rc.d aesdsocket-start-stop defaults
    
}

FILES_${PN} = "${sysconfdir}/init.d/*"
inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
#script to run in deamon
INITSCRIPT_NAME = "aesdsocket-start-stop"  
INITSCRIPT_PARAMS = "defaults"
