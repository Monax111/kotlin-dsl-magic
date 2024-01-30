plugins {
    java
}

tasks{
    test{
        useJUnitPlatform()
        sendReportOnFail()
    }
}