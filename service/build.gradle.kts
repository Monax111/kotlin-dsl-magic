println(1)
plugins {
    java
    println(2)
}
println(3)


repositories {
    mavenCentral()
}

open class MyTask : DefaultTask() {

    @Internal
    var enable : Boolean = true

    @TaskAction
    fun doSome() {
        if (enable) {
            logger.quiet("Do it")
        }
    }
}

val myTask = tasks.register<MyTask>("myTask")

myTask.get().enable = false

myTask.configure {
    enable = false
}

tasks.register("myTask1", MyTask::class.java){
    dependsOn(myTask)
}


tasks {
//    test{
//        useJUnitPlatform {
//            includeTags = setOf("integration")
//        }
//    }
}

tasks {
    val compileMain = register<MyTask>("compileMain")
    val compileTest = register<MyTask>("compileTest")
    val runTest = register<MyTask>("runTest")

    //runTest.dependsOn(compileMain,compileTest)

    runTest.configure {
        dependsOn(compileMain,compileTest)
    }

    compileMain {
        mustRunAfter(runTest)
    }


}

tasks {
    val notification = register("notification")
    val myTest = register<Test>("myTest"){
        finalizedBy(notification)
    }
}

class People {
    var name: String = ""
    var age: Int = 0
    var sex: Boolean = true
}

mutableListOf<People>().apply {

    val people = People()
    people.name = "Tim"
    people.age = 33
    people.sex = true
    add(people)

    people {
        name = "Tim"
        age = 33
        sex = true
    }


}

fun MutableList<People>.people(
    block: People.() -> Unit
) {
    add(People().apply(block))
}


fun Test.sendReportOnFail() {}

subprojects {
    tasks {
//        test {
//            sendReportOnFail()
//        }
    }
}

