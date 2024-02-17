
println("Hello world11")
println(3)

println(gradle.startParameter.taskRequests.first())

tim(gradle)

fun tim(gradle: Gradle) {
    println(2)
    println(gradle.startParameter)
}
