import org.gradle.api.tasks.testing.Test


tasks.register("integrationTests", Test::class.java).configure {
    useJUnitPlatform {
        includeTags = setOf("integration")
    }

    shouldRunAfter(tasks.named("test"))
}
