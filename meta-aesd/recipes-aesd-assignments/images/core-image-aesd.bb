inherit core-image
#IMAGE_INSTALL_append = " aesd-assignments"
CORE_IMAGE_EXTRA_INSTALL += " bash"
CORE_IMAGE_EXTRA_INSTALL += " aesd-assignments"
CORE_IMAGE_EXTRA_INSTALL += " openssh"
inherit extrausers
# See https://docs.yoctoproject.org/singleindex.html#extrausers-bbclass
# We set a default password of root to match our busybox instance setup
# Don't do this in a production image
# PASSWD below is set to the output of
# printf "%q" $(mkpasswd -m sha256crypt root) to hash the "root" password
# string
PASSWD = "\$5\$VvQwriQmYPjcXG4W\$ZxCWJWvHJo5fWubvXdMM3YhxyAstyrVkqgpPhvD7gH9"
EXTRA_USERS_PARAMS = "usermod -p '${PASSWD}' root;"

