addtask do_firmcraft_banner before do_compile
do_firmcraft_banner() {
    echo "====== Building Firmcraft BSP Image ======"
    echo "MACHINE: ${MACHINE}"
    echo "IMAGE  : ${PN}"
    echo "DATE   : `date`"
    echo "=========================================="
}

python do_build_report() {
    with open("/tmp/firmcraft-build-report.txt", "w") as f:
        f.write("Build Summary for Firmcraft BSP\n")
        f.write("Image   : %s\n" % d.getVar("PN"))
        f.write("Machine : %s\n" % d.getVar("MACHINE"))
        f.write("Date    : %s\n" % d.getVar("DATETIME"))
}
addtask do_build_report after do_image_complete

