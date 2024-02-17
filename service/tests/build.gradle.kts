plugins {
    kotlin("jvm") version "1.7.10"
}



repositories {
    mavenCentral()
}

tasks.forEachIndexed { index, task ->
    println("$index. ${task.name}")
}

tasks.test {
    useJUnitPlatform()
}

tasks.test
//dependencies.implementation("")
sourceSets.main
java

tasks.filterIsInstance<Test>()
    .filter { it.name == "test" }
    .forEach {
        it.useJUnitPlatform()
    }

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

}


fun sendTelegramMessage(report: File) {
    println(report.readText())
}

tasks {
    test {
        val originalAction = actions.first()
        val wrapper = Action<Task> {
            try {
                originalAction.execute(this)
            } finally {
                val xmlFile = this@test.reports.junitXml.outputLocation
                    .file("TEST-Test.xml").get().asFile
                sendTelegramMessage(xmlFile)
            }
        }
        actions.remove(originalAction)
        actions.add(wrapper)
    }
}

inline fun <reified T : Task> T.doOnFail(crossinline block: T.() -> Unit) {
    val originalAction = actions.first()
    val wrapper = Action<Task> {
        try {
            originalAction.execute(this)
        } finally {
            block()
        }
    }
    actions.remove(originalAction)
    actions.add(wrapper)
}

tasks {
    test {
        doOnFail {
            val xmlFile = reports.junitXml.outputLocation
                .file("TEST-Test.xml").get().asFile
            sendTelegramMessage(xmlFile)
        }
    }
}


fun Test.sendReportOnFail() {
    doOnFail {
        val xmlFile = reports.junitXml.outputLocation
            .file("TEST-Test.xml").get().asFile
        sendTelegramMessage(xmlFile)
    }
}

tasks {
    test {
        sendReportOnFail()
    }

    register<Test>("integrationTest") {
        sendReportOnFail()
    }

    withType<Test>().configureEach {
        sendReportOnFail()
    }
}
