
import org.gradle.api.tasks.testing.Test
import java.io.File

fun sendTelegramMessage(report: File) {
    println(report.readText())
}

fun Test.sendReportOnFail() {
    doOnFail {
        val xmlFile = reports.junitXml.outputLocation
            .file("TEST-Test.xml").get().asFile
        sendTelegramMessage(xmlFile)
    }
}