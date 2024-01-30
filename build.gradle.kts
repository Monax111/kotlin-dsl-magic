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
    @TaskAction
    fun doSome() {
        logger.quiet("Do it")
    }
}


tasks.register("myTask1", MyTask::class.java)

tasks {
//    test{
//        useJUnitPlatform {
//            includeTags = setOf("integration")
//        }
//    }
}

tasks {
    register<MyTask>("myTask2")
    //val myTask3 = registering(MyTask::class)

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

